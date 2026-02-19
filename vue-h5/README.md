# H5 Vue Componentization (Index)

## Component Structure

- `App.vue`
- `components/SectionHead.vue`
- `components/HeroCard.vue`
- `components/PlanList.vue`
- `components/PlanCard.vue`
- `components/SceneTabs.vue`
- `components/SkuList.vue`
- `components/SkuCard.vue`
- `components/DiyList.vue`
- `components/DiyItem.vue`
- `components/PriceBar.vue`
- `components/InquiryForm.vue`
- `components/ServiceCard.vue`
- `components/BottomNav.vue`

## API Alignment (from doc/plan.md)

- `GET /api/packages`
  - Replace `plans` mock in `App.vue`.
  - Map response fields to `PlanCard` props.
- `GET /api/packages/{id}`
  - On `PlanCard` apply, fetch details and hydrate `builderItems`.
- `POST /api/pricing/calc`
  - When DIY items change, post current selection to refresh total price.
  - Update `totalPrice` in `App.vue` and pass to `PriceBar` + `InquiryForm`.
- `POST /api/rules/validate`
  - After DIY changes, validate compatibility; map results to `builderItems` status.
- `POST /api/inquiries`
  - `InquiryForm` submit payload + selected plan + DIY items.

## Integration Notes

Data flow (recommended)
1. Page load -> `fetchPackages()` -> render `PlanList`.
2. Apply plan -> `fetchPackageDetail(planId)` -> set `builderItems` + `activePlanId`.
3. DIY change -> `validateRules(payload)` -> update item status + suggestions.
4. DIY change -> `calcPricing(payload)` -> update `totalPrice`.
5. Submit inquiry -> `submitInquiry({ contact, phone, address, area, schedule, planId, items })`.

Payload guidelines
- `calcPricing` and `validateRules` should include `planId`, `scene`, `items`.
- `submitInquiry` should include contact info + current items + quoted total.

## Files

- `services/api.js`: endpoint wrappers for integration.
- `main.js`: Vue app entry (Vite-style).
- `index.html`: local mount point for the Vue app.
