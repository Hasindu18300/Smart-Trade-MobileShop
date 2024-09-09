package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Category;
import entity.Color;
import entity.Model;
import entity.Product;
import entity.Product_Condition;
import entity.Product_Status;
import entity.Storage;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import static jdk.internal.org.jline.utils.Colors.s;
import model.HibernateUtil;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import java.nio.file.Files;

@MultipartConfig
@WebServlet(name = "ProductListing", urlPatterns = {"/ProductListing"})
public class ProductListing extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();

        Gson gson = new Gson();

        String categoryId = request.getParameter("categoryId");
        String modelId = request.getParameter("modelId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String storageId = request.getParameter("storageId");
        String colorId = request.getParameter("colorId");
        String conditionId = request.getParameter("conditionId");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");

        Part image1 = request.getPart("image1");
        Part image2 = request.getPart("image2");
        Part image3 = request.getPart("image3");

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (categoryId.equals("0")) {
            response_DTO.setContent("Please select category");
        } else if (!Validation.isInteger(categoryId)) {
            response_DTO.setContent("invalid category");
        } else if (modelId.equals("0")) {
            response_DTO.setContent("Please select model");
        } else if (!Validation.isInteger(modelId)) {
            response_DTO.setContent("invalid model");
        } else if (title.isEmpty()) {
            response_DTO.setContent("Please fill Title");
        } else if (description.isEmpty()) {
            response_DTO.setContent("Please fill Description");
        } else if (!Validation.isInteger(storageId)) {
            response_DTO.setContent("invalid storage");
        } else if (storageId.equals("0")) {
            response_DTO.setContent("Please select storage");
        } else if (!Validation.isInteger(colorId)) {
            response_DTO.setContent("invalid color");
        } else if (colorId.equals("0")) {
            response_DTO.setContent("Please select color");
        } else if (!Validation.isInteger(conditionId)) {
            response_DTO.setContent("invalid condition");
        } else if (conditionId.equals("0")) {
            response_DTO.setContent("Please select condition");
        } else if (price.isEmpty()) {
            response_DTO.setContent("Please fill Price");
        } else if (!Validation.isDouble(price)) {
            response_DTO.setContent("Invalide price");
        } else if (Double.parseDouble(price) <= 0) {
            response_DTO.setContent("Price must be grater than zero");
        } else if (quantity.isEmpty()) {
            response_DTO.setContent("Please fill Quantity");
        } else if (!Validation.isInteger(price)) {
            response_DTO.setContent("Invalide qty");
        } else if (Integer.parseInt(quantity) <= 0) {
            response_DTO.setContent("Quantity must be grater than zeroy");
        } else if (image1.getSubmittedFileName() == null) {
            response_DTO.setContent("Please upload image 1");
        } else if (image2.getSubmittedFileName() == null) {
            response_DTO.setContent("Please upload image 2");
        } else if (image3.getSubmittedFileName() == null) {
            response_DTO.setContent("Please upload image 3");
        } else {

            Category category = (Category) session.get(Category.class, Integer.parseInt(categoryId));

            if (category == null) {
                response_DTO.setContent("Please select a valid Category");
            } else {
                Model m = (Model) session.get(Model.class, Integer.parseInt(modelId));

                if (m == null) {
                    response_DTO.setContent("Please select a valid Model");
                } else {
                    if (m.getCategory().getId() != category.getId()) {
                        response_DTO.setContent("Please select a valid Category");
                    } else {
                        Storage storage = (Storage) session.get(Storage.class, Integer.parseInt(storageId));

                        if (storage == null) {
                            response_DTO.setContent("Please select a valid Storage");
                        } else {

                            Color color = (Color) session.get(Color.class, Integer.parseInt(colorId));

                            if (color == null) {
                                response_DTO.setContent("Please select a valid Color");
                            } else {
                                Product_Condition pc = (Product_Condition) session.get(Product_Condition.class, Integer.parseInt(conditionId));

                                if (pc == null) {
                                    response_DTO.setContent("Please select a valid Condition");
                                } else {
                                    Product p = new Product();
                                    p.setColor(color);
                                    p.setCondition(pc);
                                    p.setDate_time(new Date());
                                    p.setDescription(description);
                                    p.setModel(m);
                                    p.setPrice(Double.parseDouble(price));

                                    Product_Status product_Status = (Product_Status) session.load(Product_Status.class, 1);
                                    p.setProductStatus(product_Status);

                                    p.setQty(Integer.parseInt(quantity));
                                    p.setStorage(storage);
                                    p.setTitle(title);

                                    User_DTO udto = (User_DTO) request.getSession().getAttribute("user");
                                    Criteria criteria1 = session.createCriteria(User.class);
                                    criteria1.add(Restrictions.eq("email", udto.getEmail()));
                                    User user = (User) criteria1.uniqueResult();
                                    p.setUser(user);

                                    int pid = (int) session.save(p);
                                    session.beginTransaction().commit();

                                    String applicationParth = request.getServletContext().getRealPath("");
                                    String newApplicationParth = applicationParth.replace("build"+File.separator+"web", "web");

                                    File folder = new File(newApplicationParth + "//product-images//" + pid);
                                    if (!folder.exists()) { 
                                        folder.mkdir();
                                    }

                                    File file1 = new File(folder, "image1.png");
                                    InputStream inputStream1 = image1.getInputStream();
                                    Files.copy(inputStream1, file1.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    File file2 = new File(folder, "image2.png");
                                    InputStream inputStream2 = image2.getInputStream();
                                    Files.copy(inputStream2, file2.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    File file3 = new File(folder, "image3.png");
                                    InputStream inputStream3 = image3.getInputStream();
                                    Files.copy(inputStream3, file3.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    response_DTO.setSuccess(true);
                                    response_DTO.setContent("New Product Add");

                                }
                            }
                        }
                    }
                }
            }
        }
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_DTO));
        System.out.println(gson.toJson(response_DTO));
        session.close();
    }

}
