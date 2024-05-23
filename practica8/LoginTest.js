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

        const nombre = page.locator('input[name="nombre"]');
        //por que por defecto el input tiene un valor 0
        nombre.clear();
        nombre.type("Paco");

        const dni = page.locator('input[name="DNI"]');
        dni.clear();
        dni.type("122");

        sleep(3);

        const submitButton = page.locator('button[name="login"]');
        await Promise.all([page.waitForNavigation(), submitButton.click()]);

        check(page, { 
            'Listado paciente': (h2)=> page.locator('h2').textContent() === 'Listado de pacientes',
         });

        sleep(2);

    } finally {
        page.close();
    }
}