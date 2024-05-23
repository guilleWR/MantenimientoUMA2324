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
        const nombre = page.locator('input[name="nombre"]');
        nombre.clear();//por que por defecto el input podria tener un valor puesto
        nombre.type("Paco");

        const dni = page.locator('input[name="DNI"]');
        dni.clear();
        dni.type("122");

        sleep(2);

        //Pulsamos el boton login
        const submitButton = page.locator('button[name="login"]');
        await Promise.all([page.waitForNavigation(), submitButton.click()]);

        check(page, { 
            //Comprobamos que carge la pagina Listado paciente
            'Listado paciente': (h2)=> page.locator('h2').textContent() === 'Listado de pacientes',
         });

        sleep(2);

    } finally {
        page.close();
    }
}