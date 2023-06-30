import fetch from '@/utils/fetch';

export function create(body) {
    return fetch.post('/api/verify', body);
}

export function remove(id) {
    return fetch.remove('/api/verify', id);
}

export function update(id, body) {
    return fetch.put('/api/verify', id, body);
}

export function get(id) {
    return fetch.get('/api/verify', id);
}

export function query(params) {
    return fetch.get('/api/verify', null, params);
}

export function events(params) {
    return fetch.get('/api/verify/events', null, params);
}

export function myEvents(params) {
    return fetch.get('/api/verify/myEvents', null, params);
}

export function exportExcel(params) {
    return fetch.download('/api/verify/exportExcel', params);
}


export default {
    create,
    remove,
    update,
    get,
    query,
    events,
    myEvents,
    exportExcel
};
