import http from 'k6/http';
import { check } from 'k6';

export default function () {
    let res = http.get('http://localhost:8080/medico/1');
    check(res, {
        'status was 200': (r) => r.status === 200,
    });
}


export const options = {
    scenarios: {
        breakpoint: {
            executor: 'ramping-arrival-rate',
            preAllocatedVUs: 1000, // VUs alocados inicialmente
            maxVUs: 100000, // VUs máximo (al menos 100,000)
            stages: [
                { duration: '10m', target: 100000 }, // Aumentar hasta 100,000 usuarios en 10 minutos
            ],
        },
    },
    thresholds: {
        http_req_failed: [{ threshold: 'rate<=0.01', abortOnFail: true }], // Abortar si más del 5% de las solicitudes fallan
    },
};

