const API_BASE = '/api';

export const fetchPackages = () =>
  fetch(`${API_BASE}/packages`).then(res => res.json());

export const fetchPackageDetail = (id) =>
  fetch(`${API_BASE}/packages/${id}`).then(res => res.json());

export const calcPricing = (payload) =>
  fetch(`${API_BASE}/pricing/calc`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  }).then(res => res.json());

export const validateRules = (payload) =>
  fetch(`${API_BASE}/rules/validate`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  }).then(res => res.json());

export const submitInquiry = (payload) =>
  fetch(`${API_BASE}/inquiries`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  }).then(res => res.json());
