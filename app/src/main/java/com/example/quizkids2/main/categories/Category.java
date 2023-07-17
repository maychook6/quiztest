package com.example.quizkids2.main.categories;

class Category {
    String title;

    boolean isChecked;

    Category(String title, boolean isChecked) {
        this.title = title;
        this.isChecked = isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
