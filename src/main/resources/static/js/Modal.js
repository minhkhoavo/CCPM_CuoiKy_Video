class Modal {
    constructor(modalId, name, formActionAdd, formActionEdit) {
        this.name = name
        this.modal = document.getElementById(modalId);
        this.form = this.modal.querySelector('#employeeForm'); // Sử dụng querySelector thay vì getElementById
        this.formActionAdd = formActionAdd;
        this.formActionEdit = formActionEdit;
        this.modalTitle = this.modal.querySelector('#modalTitle');
        this.actionButton = this.modal.querySelector('#actionButton');
        this.imagePreview = this.modal.querySelector('#previewImg');
    }

    open(data = null) {
        if (data) {
            // Chế độ chỉnh sửa
            this.form.action = this.formActionEdit + "/" + data.id;
            this.modalTitle.innerText = "Edit " + name;
            this.actionButton.innerText = "Edit " + name;

            Object.keys(data).forEach(key => {
                const input = this.modal.querySelector(`[name="${key}"]`);
                if (input) {
                    input.value = data[key] || ""; // Gán giá trị cho input
                }
            });


            if (data.imageUrl) {
                this.imagePreview.src = data.imageUrl;
                this.imagePreview.classList.remove('hidden');
            }
        } else {
            console.log(this.form);
            this.form.action = this.formActionAdd;
            this.modalTitle.innerText = "New " + name;
            this.form.reset(); // Đặt lại tất cả input
            this.imagePreview.classList.add('hidden');
        }

        // Hiển thị modal
        this.modal.classList.remove('hidden');
    }

    close() {
        this.modal.classList.add('hidden');
    }
}
