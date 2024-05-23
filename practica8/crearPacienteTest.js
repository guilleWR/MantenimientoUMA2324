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

        // const botonAnyadirCuenta = page.locator('a[name="add"]');
        // await Promise.all([page.waitForNavigation(), botonAnyadirCuenta.click()]);

        sleep(1);

        let nombre = page.locator('input[name="nombre"]');
        //por que por defecto el input tiene un valor 0
        nombre.clear();
        nombre.type("Paco");

        let dni = page.locator('input[name="DNI"]');
        dni.clear();
        dni.type("122");

        sleep(2);

        let submitButton = page.locator('button[name="login"]');
        await Promise.all([page.waitForNavigation(), submitButton.click()]);

        sleep(2);

        const addButton = page.locator('button[name="add"]');
        await Promise.all([page.waitForNavigation(), addButton.click()]);

        sleep(2);

        nombre = page.locator('input[name="nombre"]');
        nombre.clear();
        nombre.type("Eustaquio");

        dni = page.locator('input[name="dni"]');
        dni.clear();
        dni.type("12345678Z");

        const edad = page.locator('input[name="edad"]');
        edad.clear();
        edad.type("101");

        const cita = page.locator('input[name="cita"]');
        cita.clear();
        cita.type("citado");

        sleep(2);

        const submit2Button = page.locator('button[type="submit"]');
        await Promise.all([page.waitForNavigation(), submit2Button.click()]);

        sleep(2);


        let len = page.$$("table tbody tr").length;

        check(page, {
            'Nombre Paciente': p =>  p.$$("table tbody tr")[len - 1]
                .$('td[name="nombre"]').textContent().includes("Eustaquio"),

            'Dni Paciente': p => p.$$("table tbody tr")[len - 1]
                .$('td[name="dni"]').textContent().includes("12345678Z"),

        });

        sleep(2);

    } finally {
        page.close();
    }
}