async function loadCartItems() {

    const response = await fetch(
            "LoadCartItems"
            );
    const popup = Notification();
    if (response.ok) {
        const json = await response.json();
        if (json.length == 0) {
            popup.error({
                message: "Cart is Empty"
            });
            //window.location = "index.html";
        } else {

            console.log(json);

            let cartItemContainer = document.getElementById("cart-item-container");
            let cartItemRow = document.getElementById("cart-item-row");
            cartItemContainer.innerHTML = "";


            let totalQty = 0;
            let total = 0;

            json.forEach(item => {
                let itemSubTotal = item.product.price * item.qty;

                totalQty += item.qty;
                total += itemSubTotal;

                let carItemRowClone = cartItemRow.cloneNode(true);
                carItemRowClone.querySelector("#cart-item-a").href = "single-product.html?id=" + item.product.id;
                carItemRowClone.querySelector("#cart-item-img").src = "product-images/" + item.product.id + "/image1.png";
                carItemRowClone.querySelector("#cart-item-title").innerHTML = item.product.title;
                carItemRowClone.querySelector("#cart-item-price").innerHTML = new Intl.NumberFormat().format(item.product.price);

                carItemRowClone.querySelector("#cart-item-qty").value = item.qty;
                carItemRowClone.querySelector("#cart-item-subtotal").innerHTML = (itemSubTotal);

                cartItemContainer.appendChild(carItemRowClone);

                document.getElementById("cart-qty-total").innerHTML = totalQty;
                document.getElementById("cart-total").innerHTML = total;


            }

            );


        }

    } else {
        popup.error({
            message: "Unable to requset your reaquest"
        });
    }
}