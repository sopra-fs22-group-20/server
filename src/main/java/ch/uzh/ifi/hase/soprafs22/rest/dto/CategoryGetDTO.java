package ch.uzh.ifi.hase.soprafs22.rest.dto;

public class CategoryGetDTO {

    private Long categoryId;
    private String category;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

