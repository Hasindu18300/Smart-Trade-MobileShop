async function loadProduct() {

    const parameters = new URLSearchParams(window.location.search);
//    const productId = parameters.get("id");
//    console.log(productId);


    if (parameters.has("id")) {
        const productId = parameters.get("id");
        const response = await fetch("LoadSingalProduct?id=" + productId);
        if (response.ok) {
            const json = await response.json();
            console.log(json.product.id);
            const id = json.product.id;
            document.getElementById("image1").src = "product-images/" + id + "/image1.png";
            document.getElementById("image2").src = "product-images/" + id + "/image2.png";
            document.getElementById("image3").src = "product-images/" + id + "/image3.png";
            document.getElementById("image1-thumb").src = "product-images/" + id + "/image1.png";
            document.getElementById("image2-thumb").src = "product-images/" + id + "/image2.png";
            document.getElementById("image3-thumb").src = "product-images/" + id + "/image3.png";
            document.getElementById("product-title").innerHTML = json.product.title;
            document.getElementById("product-published-on").innerHTML = json.product.date_time;
            document.getElementById("product-price").innerHTML = new Intl.NumberFormat().format(json.product.price);
            document.getElementById("product-category").innerHTML = json.product.model.category.name;
            document.getElementById("product-model").innerHTML = json.product.model.name;
            document.getElementById("product-condition").innerHTML = json.product.condition.name;
            document.getElementById("product-qty").innerHTML = json.product.qty;
            document.getElementById("color-border").style.borderColor = json.product.color.name;
            document.getElementById("color-background").style.backgroundColor = json.product.color.name;
            document.getElementById("product-storage").innerHTML = json.product.storage.value;
            document.getElementById("description").innerHTML = json.product.description;
            document.getElementById("add-to-cart-main").addEventListener(
                    "click",
                    (e) => {
                addToCart(json.product.id,
                        document.getElementById("add-to-cart-qty").value
                        );
                e.preventDefault();
            });
            let ProductHtml = document.getElementById("similer-product");
            document.getElementById("similer-product-main").innerHTML = "";
            json.productList.forEach(item => {

                let ProductCloneHtml = ProductHtml.cloneNode(true);
                ProductCloneHtml.querySelector("#similer-product-a1").href = "single-product.html?id" + item.id;
                ProductCloneHtml.querySelector("#similer-product-image").src = "product-images/" + item.id + "/image1.png";
                ProductCloneHtml.querySelector("#similer-product-a2").href = "single-product.html?id" + item.id;
                ProductCloneHtml.querySelector("#similer-product-title").innerHTML = item.title;
                ProductCloneHtml.querySelector("#similer-product-storage").innerHTML = item.storage.value;
                ProductCloneHtml.querySelector("#similer-product-price").innerHTML = new Intl.NumberFormat().format(json.product.price);
                ProductCloneHtml.querySelector("#similer-product-color-border").style.borderColor = item.color.name;
                ProductCloneHtml.querySelector("#similer-product-color").style.backgroundColor = item.color.name;
                ProductCloneHtml.querySelector("#similer-product-add-to-cart").addEventListener(
                        "click",
                        (e) => {
                    addToCart(item.id, 1);
                    e.preventDefault();
                }

                );
                document.getElementById("similer-product-main").appendChild(ProductCloneHtml);
            });
            $('.recent-product-activation').slick({
                infinite: true,
                slidesToShow: 4,
                slidesToScroll: 4,
                arrows: true,
                dots: false,
                prevArrow: '<button class="slide-arrow prev-arrow"><i class="fal fa-long-arrow-left"></i></button>',
                nextArrow: '<button class="slide-arrow next-arrow"><i class="fal fa-long-arrow-right"></i></button>',
                responsive: [{
                        breakpoint: 1199,
                        settings: {
                            slidesToShow: 3,
                            slidesToScroll: 3
                        }
                    },
                    {
                        breakpoint: 991,
                        settings: {
                            slidesToShow: 2,
                            slidesToScroll: 2
                        }
                    },
                    {
                        breakpoint: 479,
                        settings: {
                            slidesToShow: 1,
                            slidesToScroll: 1
                        }
                    }
                ]
            });
        } else {
            window.location = "index.html";
        }

    } else {
        window.location = "index.html";
    }
}



async function addToCart(id, qty) {

    const response = await fetch(
            "AddToCart?id=" + id + "&qty=" + qty
            );

    const popup = Notification();
    if (response.ok) {
        const json = await response.json();
        if (json.success) {

            popup.success({
                message: json.content
            });

        } else {
            popup.error({
                message: json.content
            });
        }


    } else {
        popup.error({
            message: "Unable to requset your reaquest"
        });
    }

}  