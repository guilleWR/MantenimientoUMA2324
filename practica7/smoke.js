import http from 'k6/http';
import { check } from 'k6';

export default function () {
    let res = http.get('http://localhost:8080/medico/1');
    check(res, {
        'status was 200': (r) => r.status === 200,//Asegurar que las respuestas tengan código 200
    });
    
}

export const options = {
    vus: 5, // 5 usuarios virtuales
    duration: '1m', // Duración de 1 minuto
    thresholds: {
        // El promedio de las solicitudes debe ser menor a 100 ms
        http_req_duration: [{ threshold: 'avg<100', abortOnFail: true }],
        // Asegurar que el 100% de las respuestas tengan código 200
        checks: [{ threshold: 'rate==1', abortOnFail: true }],
        // No debe haber peticiones fallidas
        http_req_failed: [{ threshold: 'rate==0', abortOnFail: true }]
    },
};