const PRODUCT_API_URL = "http://localhost:8080/ecommerce-server/api/v1";
const COMMENT_API_URL = "http://localhost:8080/ecommerce-server/api/v1/comment";
export async function getProducts() {
    const res = await fetch(`${PRODUCT_API_URL}/product_list`);

    // fetch won't throw error on 400 errors (e.g. when URL is wrong), so we need to do it manually. This will then go into the catch block, where the message is set
    if (!res.ok) throw Error("Failed getting product");
  
    const { data } = await res.json();
    return data;
}
export async function getProduct(id) {
    const res = await fetch(`${PRODUCT_API_URL}/product/${id}`);

    // fetch won't throw error on 400 errors (e.g. when URL is wrong), so we need to do it manually. This will then go into the catch block, where the message is set
    if (!res.ok) throw Error("Failed getting product");
    const data = await res.json();
    return data;
}
export async function getVariationInfor(id) {
    const res = await fetch(`${PRODUCT_API_URL}/product/sku/${id}`);

    // fetch won't throw error on 400 errors (e.g. when URL is wrong), so we need to do it manually. This will then go into the catch block, where the message is set
    if (!res.ok) throw Error("Failed getting product");
    const data = await res.json();
    return data;
}
export async function getComments({productId, page = 0}) {
    const res = await fetch(`${COMMENT_API_URL}/product/${productId}?page=${page}`)
    if (!res.ok) throw Error("Failed getting product");
    const data = await res.json();
    return data;
}