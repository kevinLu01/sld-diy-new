# Repository Guidelines

## Project Structure & Module Organization

This repo is a static prototype with two HTML pages and a product plan doc.

- `index.html`: mobile storefront (DIY商城) prototype with inline CSS and small JS interactions.
- `admin.html`: admin console prototype with inline CSS.
- `doc/plan.md`: product plan and technical roadmap.

There is no `src/` or `tests/` directory; everything lives in the HTML files.

## Build, Test, and Development Commands

No build system is configured. Use a static server for local preview when needed.

- `python3 -m http.server 8000`: serve the repo and open `http://localhost:8000/index.html`.
- Open files directly in a browser if you do not need a server.

## Coding Style & Naming Conventions

- Indentation: 2 spaces in HTML/CSS/JS to match existing files.
- CSS: keep shared colors and spacing in `:root` variables; prefer class-based styling.
- Class names: lower-kebab-case (e.g., `.plan-card`, `.bottom-nav`).
- JS: keep interactions minimal and inline in the page’s `<script>` block.

No formatter or linter is configured; keep diffs small and consistent with existing style.

## Testing Guidelines

No automated tests exist. Do lightweight manual checks:

- Visual review on mobile-sized viewports (around 390–430px width).
- Click through UI interactions (tabs, plan selection) on `index.html`.

## Commit & Pull Request Guidelines

Git history only contains the initial commit, so no established message convention. Use a short, imperative summary (e.g., "Update pricing card layout").

For PRs:

- Include a concise summary of changes.
- Add screenshots for UI changes to `index.html` or `admin.html`.
- Link any relevant issue or plan section in `doc/plan.md`.

## Notes

Keep Chinese UI copy consistent with existing terminology (e.g., 方案包, 询价, 兼容) and avoid mixing simplified/traditional variants in the same section.
