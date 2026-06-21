let currentResource = 'products';
let currentPage = 0;
const pageSize = 10;
let currentPageInfo = null;
let showAllRows = false;
let usingClientPagingFallback = false;

const config = {
    products: { fields: ['name', 'price', 'categoryId'], headers: ['ID', 'Name', 'Price', 'Category ID'] },
    categories: { fields: ['name'], headers: ['ID', 'Name'] },
    customers: { fields: ['name', 'email'], headers: ['ID', 'Name', 'Email'] },
    orders: { fields: ['customerId', 'items'], headers: ['ID', 'Customer ID', 'Items'] },
    'order-items': { fields: ['orderId', 'productId', 'quantity'], headers: ['ID', 'Order ID', 'Product ID', 'Quantity'] }
};

function loadEntity(resource) {
    currentResource = resource;
    currentPage = 0;
    showAllRows = false;
    document.querySelectorAll('#entityTabs .nav-link').forEach(btn => {
        btn.classList.toggle('active', btn.dataset.resource === resource);
    });
    renderForm();
    refreshData();
}

function renderForm() {
    const container = document.getElementById('form-container');
    const fields = config[currentResource].fields;
    let html = '';

    fields.forEach(field => {
        html += `<div class="mb-2">
            <label class="small" for="input-${field}">${field}</label>
            <input type="text" id="input-${field}" class="form-control form-control-sm">
        </div>`;
    });

    html += `<button onclick="handleCreate()" class="btn btn-success btn-sm w-100 mt-2">Execute POST</button>`;
    container.innerHTML = html;
}

async function refreshData() {
    const head = document.getElementById('table-head');
    const body = document.getElementById('table-body');
    head.innerHTML = `<tr>${config[currentResource].headers.map(h => `<th>${h}</th>`).join('')}<th>Actions</th></tr>`;

    let response;
    try {
        response = await loadCurrentData();
    } catch (error) {
        currentPageInfo = null;
        body.innerHTML = `
            <tr>
                <td colspan="${config[currentResource].headers.length + 1}" class="text-danger p-3">
                    ${error.message}
                </td>
            </tr>
        `;
        renderDataControls(0);
        return;
    }

    const data = Array.isArray(response) ? response : response.content;
    currentPageInfo = !showAllRows ? response : null;

    body.innerHTML = data.map(item => `
        <tr>
            <td>${item.id ?? ''}</td>
            ${config[currentResource].fields.map(field => `<td>${formatValue(item[field])}</td>`).join('')}
            <td>
                <button class="btn btn-outline-primary btn-sm" onclick="handlePut(${item.id})">PUT</button>
                <button class="btn btn-outline-warning btn-sm" onclick="handlePatch(${item.id})">PATCH</button>
                <button class="btn btn-outline-danger btn-sm" onclick="handleDelete(${item.id})">DELETE</button>
            </td>
        </tr>
    `).join('');

    renderDataControls(Array.isArray(response) ? data.length : response.totalElements);
}

async function loadCurrentData() {
    if (!showAllRows) {
        try {
            const page = await Api.get(`${currentResource}/page?page=${currentPage}&size=${pageSize}&sort=id,asc`);
            usingClientPagingFallback = false;
            return page;
        } catch (error) {
            const rows = await Api.get(currentResource);
            usingClientPagingFallback = true;
            return {
                content: rows.slice(currentPage * pageSize, (currentPage + 1) * pageSize),
                totalElements: rows.length,
                totalPages: Math.max(1, Math.ceil(rows.length / pageSize)),
                number: currentPage,
                size: pageSize
            };
        }
    }

    usingClientPagingFallback = false;
    return Api.get(currentResource);
}

function renderDataControls(totalItems) {
    const controls = document.getElementById('data-controls');

    if (showAllRows) {
        controls.innerHTML = `
            <span class="badge bg-warning text-dark">All rows: ${totalItems}</span>
            <button class="btn btn-outline-light btn-sm" onclick="showPagedRows()">Paged view</button>
        `;
        return;
    }

    const totalPages = currentPageInfo?.totalPages ?? 1;
    const totalElements = currentPageInfo?.totalElements ?? totalItems;
    const mode = usingClientPagingFallback ? 'Client paged' : 'Paged';
    controls.innerHTML = `
        <span class="badge bg-info text-dark">${mode}: ${currentPage + 1}/${totalPages}, total ${totalElements}</span>
        <button class="btn btn-outline-light btn-sm" onclick="previousPage()" ${currentPage === 0 ? 'disabled' : ''}>Prev</button>
        <button class="btn btn-outline-light btn-sm" onclick="nextPage()" ${currentPage + 1 >= totalPages ? 'disabled' : ''}>Next</button>
        <button class="btn btn-outline-light btn-sm" onclick="showAllDataRows()">Show all</button>
    `;
}

function showAllDataRows() {
    showAllRows = true;
    refreshData();
}

function showPagedRows() {
    showAllRows = false;
    currentPage = 0;
    refreshData();
}

function previousPage() {
    if (currentPage > 0) {
        currentPage -= 1;
        refreshData();
    }
}

function nextPage() {
    if (!currentPageInfo || currentPage + 1 < currentPageInfo.totalPages) {
        currentPage += 1;
        refreshData();
    }
}

async function handleCreate() {
    await Api.post(currentResource, readFormPayload());
    refreshData();
}

async function handlePut(id) {
    await Api.put(currentResource, id, readFormPayload());
    refreshData();
}

async function handleDelete(id) {
    if (confirm('Delete this record?')) {
        await Api.delete(currentResource, id);
        refreshData();
    }
}

async function handlePatch(id) {
    const field = config[currentResource].fields[0];
    const value = prompt(`New value for ${field}:`);

    if (value) {
        await Api.patch(currentResource, id, { [field]: parseInputValue(field, value) });
        refreshData();
    }
}

function readFormPayload() {
    const payload = {};

    config[currentResource].fields.forEach(field => {
        const value = document.getElementById(`input-${field}`).value;
        payload[field] = parseInputValue(field, value);
    });

    return payload;
}

function parseInputValue(field, value) {
    if (field === 'items') {
        return value ? JSON.parse(value) : [];
    }

    return value !== '' && !isNaN(value) ? Number(value) : value;
}

function formatValue(value) {
    if (Array.isArray(value) || typeof value === 'object' && value !== null) {
        return JSON.stringify(value);
    }

    return value ?? '';
}

window.onload = () => loadEntity('products');
