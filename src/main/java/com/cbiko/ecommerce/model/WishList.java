package com.cbiko.ecommerce.model;

import javax.persistence.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "wishlist")
public class WishList {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    public WishList() {
    }


    public WishList(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Transient
    public List<Product> getItems() {
        List<Product> items = new ArrayList<>();
        items.add(product); // Sadece tek bir ürün eklendiğini varsayalım
        return items;
    }


}