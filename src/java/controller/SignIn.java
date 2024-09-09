package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.tools.doclint.Entity;
import dto.Cart_DTO;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Cart;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "SignIn", urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        User_DTO user_DTO = gson.fromJson(request.getReader(), User_DTO.class);

        //verification part
        if (user_DTO.getEmail().isEmpty()) {
            response_DTO.setContent("Please enter your email");

        } else if (user_DTO.getPassword().isEmpty()) {
            response_DTO.setContent("Please enter your Password");

        } else {

            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
            criteria1.add(Restrictions.eq("password", user_DTO.getPassword()));

            if (!criteria1.list().isEmpty()) {

                User user = (User) criteria1.list().get(0);

                if (!user.getVerification().equals("Verified")) {
                    //not verified
                    request.getSession().setAttribute("email", user_DTO.getEmail());
                    response_DTO.setContent("Unverified");

                } else {

                    //verified
                    user_DTO.setFirst_name(user.getFirst_name());
                    user_DTO.setLast_name(user.getLast_name());
                    user_DTO.setPassword(null);
                    request.getSession().setAttribute("user", user_DTO);

                    //Transfer Session Cart to Db Cart
                    if (request.getSession().getAttribute("sessionCart") != null) {
                        ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) request.getSession().getAttribute("sessionCart");

                        Criteria criteri2 = session.createCriteria(Cart.class);
                        criteri2.add(Restrictions.eq("user", user));
                        List<Cart> dbCart = criteri2.list();

                        if (dbCart.isEmpty()) {
                            //DB Cart is Empty
                            //Add all session cart items into Db CArt
                            for (Cart_DTO cart_DTO : sessionCart) {
                                Cart cart = new Cart();
                                cart.setProduct(cart_DTO.getProduct());
                                cart.setQty(cart_DTO.getQty());
                                cart.setUser(user);
                                session.save(cart);
                            }

                        } else {
                            //Found items to DB class

                            for (Cart_DTO cart_DTO : sessionCart) {
                                boolean isFoundInDBCart = false;

                                for (Cart cart : dbCart) {

                                    if (cart_DTO.getProduct().getId() == cart.getProduct().getId()) {
                                        //same item found in session Cart & DB Cart
                                        isFoundInDBCart = true;

                                        if ((cart_DTO.getQty() + cart.getQty()) <= cart.getProduct().getQty()) {
                                            //qty avaliable
                                            cart.setQty(cart_DTO.getQty() + cart.getQty());
                                            session.update(cart);
                                        } else {
                                            //qty not avaliable
                                            //set max avaliable qty
                                            cart.setQty(cart.getProduct().getQty());
                                            session.update(cart);
                                        }
                                    }

                                }
                                if (!isFoundInDBCart) {
                                    //not found DB CArt

                                    Cart cart = new Cart();
                                    cart.setProduct(cart_DTO.getProduct());
                                    cart.setQty(cart_DTO.getQty());
                                    cart.setUser(user);
                                    session.save(cart);
                                }

                            }

                        }

                        request.getSession().removeAttribute("sessionCart");
                        session.beginTransaction().commit();

                    }

                    response_DTO.setSuccess(true);
                    response_DTO.setContent("Sign In Success");
                }

            } else {
                response_DTO.setContent("Invalid Details! Please try again Later..");
            }

        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_DTO));
        System.out.println(gson.toJson(response_DTO));

    }

}
