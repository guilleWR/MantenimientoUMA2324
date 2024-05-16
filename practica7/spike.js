import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '30s', target: 400 }, // Subimos a 400 VUs en 30 segundos
        { duration: '1m', target: 400 }, // Mantenemos 400 VUs durante 1 minuto
        { duration: '30s', target: 0 }, // Bajamos a 0 VUs en 30 segundos
    ],
    thresholds: {
        http_req_failed: [{ threshold: 'rate<=0.005', abortOnFail: true }], // Las peticiones fallidas deben ser menores al 0.5%
    },
};

export default function () {
    let res = http.get('http://localhost:8080/medico/1');
    check(res, {
        'status was 200': (r) => r.status === 200,
    });
}