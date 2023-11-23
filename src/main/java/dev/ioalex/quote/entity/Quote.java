package dev.ioalex.quote.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Entity
@Table(name = "QUOTE")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "AUTHOR")
    @Size(max = 128)
    private String author;

    @NonNull
    @Column(name = "TEXT")
    @Size(max = 1024)
    private String text;

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote quote)) return false;
        return Objects.equals(getAuthor(), quote.getAuthor()) && Objects.equals(getText(), quote.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor(), getText());
    }

    @Override
    public String toString() {
        return "quote{" +
                "'" + text + '\'' +
                ", by'" + author + '\'' +
                '}';
    }
}
