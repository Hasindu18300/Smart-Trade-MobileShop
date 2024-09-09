package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "qty", nullable = false)
    private int qty;

    @Column(name = "date_time", nullable = false)
    private Date date_time;

    public Product() {
    }

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "condition_id")
    private Product_Condition condition;

    @ManyToOne
    @JoinColumn(name = "product_status_id")
    private Product_Status productStatus;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public Date getDate_time() {
        return date_time;
    }

    public Model getModel() {
        return model;
    }

    public Storage getStorage() {
        return storage;
    }

    public Color getColor() {
        return color;
    }

    public User getUser() {
        return user;
    }

    public Product_Condition getCondition() {
        return condition;
    }

    public Product_Status getProductStatus() {
        return productStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCondition(Product_Condition condition) {
        this.condition = condition;
    }

    public void setProductStatus(Product_Status productStatus) {
        this.productStatus = productStatus;
    }

}
