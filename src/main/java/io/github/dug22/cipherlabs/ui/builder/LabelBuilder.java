package io.github.dug22.cipherlabs.ui.builder;


import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class LabelBuilder {

    public static class Builder {

        private String text = "Default text";
        private String fontFamily = "Arial";
        private boolean isBold = false;
        private int fontSize = 12;
        private Color textColor = Color.BLACK;
        private Cursor cursor;
        private Double maxWidth;
        private Double maxHeight;
        private Pos alignmentPosition;
        private boolean wrapText = false;
        private TextAlignment textAlignment;
        private double xLayout = -1.0;
        private double yLayout = -1.0;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setFontFamily(String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        public Builder setBold(boolean isBold) {
            this.isBold = isBold;
            return this;
        }

        public Builder setFontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder setTextColor(Color textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setCursor(Cursor cursor) {
            this.cursor = cursor;
            return this;
        }

        public Builder setMaxWidth(Double maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public Builder setMaxHeight(Double maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        public Builder setAlignment(Pos alignmentPosition) {
            this.alignmentPosition = alignmentPosition;
            return this;
        }

        public Builder setWrapText(boolean wrapText) {
            this.wrapText = wrapText;
            return this;
        }

        public Builder setTextAlignment(TextAlignment textAlignment){
            this.textAlignment = textAlignment;
            return this;
        }

        public Builder setLayoutX(double xLayout){
            this.xLayout = xLayout;
            return this;
        }

        public Builder setLayoutY(double yLayout){
            this.yLayout = yLayout;
            return this;
        }

        public Label build() {
            Label label = new Label();
            label.setText(text);
            if (isBold) {
                label.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));
            } else {
                label.setFont(Font.font(fontFamily, fontSize));
            }
            label.setTextFill(textColor);
            if (cursor != null) {
                label.setCursor(cursor);
            }

            if (maxWidth != null) {
                label.setMaxWidth(maxWidth);
            }

            if (maxHeight != null) {
                label.setMaxHeight(maxHeight);
            }

            if (alignmentPosition != null) {
                label.setAlignment(alignmentPosition);
            }

            if (wrapText) {
                label.setWrapText(true);
            }

            if(textAlignment != null){
                label.setTextAlignment(textAlignment);
            }

            if(xLayout != -1.0){
                label.setLayoutX(xLayout);
            }

            if(yLayout != -1.0){
                label.setLayoutY(yLayout);
            }

            return label;
        }
    }
}
