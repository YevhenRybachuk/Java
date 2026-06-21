const BASE_URL = "http://localhost:8080";

const Api = {
    async get(resource) {
        const res = await fetch(`${BASE_URL}/${resource}`);
        if (!res.ok) {
            throw new Error(`GET /${resource} failed with status ${res.status}`);
        }
        return res.json();
    },

    async post(resource, data) {
        return fetch(`${BASE_URL}/${resource}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        }).then(res => res.json());
    },

    async put(resource, id, data) {
        return fetch(`${BASE_URL}/${resource}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        }).then(res => res.json());
    },

    async patch(resource, id, data) {
        return fetch(`${BASE_URL}/${resource}/${id}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        }).then(res => res.json());
    },

    async delete(resource, id) {
        return fetch(`${BASE_URL}/${resource}/${id}`, { method: 'DELETE' });
    }
};
