import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '3m', target: 500 }, // Subimos a 500 VUs en 3 minutos
        { duration: '3m', target: 500 }, // Mantenemos 500 VUs durante 3 minutos
        { duration: '2m', target: 0 }, // Bajamos a 0 VUs en 2 minutos
    ],
    thresholds: {
        http_req_failed: [{ threshold: 'rate<=0.01', abortOnFail: true }], // Las peticiones fallidas deben ser menores al 1%
    },
};

export default function () {
    let res = http.get('http://localhost:8080/medico/1');
    check(res, {
        'status was 200': (r) => r.status === 200,
    });
}