// Validación simple de formularios
document.addEventListener("DOMContentLoaded", () => {
    const forms = document.querySelectorAll("form");
    forms.forEach(form => {
        form.addEventListener("submit", e => {
            const inputs = form.querySelectorAll("input[required]");
            let valid = true;
            inputs.forEach(input => {
                if (!input.value.trim()) {
                    valid = false;
                    input.style.borderColor = "red";
                } else {
                    input.style.borderColor = "#ccc";
                }
            });
            if (!valid) {
                e.preventDefault();
                alert("Por favor completa todos los campos obligatorios.");
            }
        });
    });
});
