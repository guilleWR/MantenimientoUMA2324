import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '10m', target: 100000 }, // Aumentar hasta 100,000 usuarios en 10 minutos
    ],
    thresholds: {
        http_req_failed: [{ threshold: 'rate<=0.01', abortOnFail: true }], // Abortar si más del 1% de las solicitudes fallan
    },
};

export default function () {
    let res = http.get('http://localhost:8080/medico/1');
    check(res, {
        'status was 200': (r) => r.status === 200,
    });
}