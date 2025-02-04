import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import GlobalStyle from "./style/GlobalStyle";
import AppLayout from "./ui/AppLayout";
import HomePage from "./pages/HomePage";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import Login from "./pages/Login";
import Product from "./features/products/Product";
import Cart from "./features/cart/Cart";
import { CartContextProvider } from "./context/CartContext";
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      // staleTime: 60 * 1000,
      staleTime: 0,
    },
  },
});

function App() {
  return (
    <CartContextProvider>
        <QueryClientProvider client={queryClient}>
        <ReactQueryDevtools initialIsOpen={false} />
        <GlobalStyle />
        <BrowserRouter>
          <Routes>
            <Route element={<AppLayout />}>
              <Route path="/" element={<HomePage />} />
              <Route path="product/:productId" element={<Product />} />
            </Route>
            <Route path="cart" element={<Cart/>}/>
            <Route path="login" element={<Login />} />
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </CartContextProvider>
  );
}

export default App;
