// Westerhof Rodríguez Guillermo Alejandro
// Rueda Cabrera Pedro

import { browser } from 'k6/experimental/browser';
import { check, sleep } from 'k6';

export const options = {
    scenarios: {
        ui: {
            executor: "shared-iterations", // para realizariteraciones sin indicar el tiempo
            options: {
                browser: {
                    type: "chromium",
                },
            },
        },
    },
    thresholds: {
        checks: ["rate==1.0"],
    },
};

export default async function () {
    const page = browser.newPage();
    try {
        await page.goto("http://localhost:4200");

        sleep(1);

        //Rellenamos el login
        let nombre = page.locator('input[name="nombre"]'); //por que por defecto el input tiene un valor 0
        nombre.clear();
        nombre.type("Paco");

        let dni = page.locator('input[name="DNI"]');
        dni.clear();
        dni.type("122");

        sleep(2);

        //Pulsamos el boton login
        let submitButton = page.locator('button[name="login"]');
        await Promise.all([page.waitForNavigation(), submitButton.click()]);

        sleep(2);

        //Espera a que la tabla esté cargada
        await page.waitForSelector('table tbody tr');

        //Obtenemos todas las filas de la tabla
        const rows = await page.$$('table tbody tr');
        const len = rows.length;

        sleep(1);

        // Hacemos click en la última fila
        if (len > 0) {
            console.log(len);
            await rows[len - 1].click();
            sleep(2);
        }

        //Pulsamos el boton view
        submitButton = page.locator('button[name="view"]');
        await Promise.all([page.waitForNavigation(), submitButton.click()]);
        
        sleep(1);

        //Pulsamos el boton añadir informe
        submitButton = page.locator('button[name="add"]');
        await Promise.all([page.waitForNavigation(), submitButton.click()]);
        
        //Rellenamos el informe
        const informe = page.locator('textarea');
        informe.clear();
        sleep(1);
        informe.type("Hola amigos");
        sleep(1);
        submitButton = page.locator('button[name="save"]');
        await Promise.all([page.waitForNavigation(), submitButton.click()]);

        
        sleep(2); 
        
        check(page, {
            //Combrobamos que se vea el informe correctamente
            'Informe ': ()=> page.locator('span[name="content"]').textContent().includes("Hola amigos"),
        });

        sleep(2);

    } finally {
        page.close();
    }
}