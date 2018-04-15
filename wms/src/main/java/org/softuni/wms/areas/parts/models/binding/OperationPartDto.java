package org.softuni.wms.areas.parts.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OperationPartDto {

    @NotEmpty(message = "You need to select part.")
    private String id;
    @NotNull(message = "Q-ty cannot be null.")
    @Min(value = 0, message = "Q-ty must be positive.")
    private Long quantity;

    public OperationPartDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
