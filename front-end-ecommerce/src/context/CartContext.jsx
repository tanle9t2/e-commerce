import { createContext, useContext } from "react";
import { useLocalStorageState } from "../hook/useLocalStorageState"

const CartContext = createContext();

function CartContextProvider({ children }) {
  const [cartItemTick, setCartItemTick] = useLocalStorageState(
   [],"cartItemTick"
  );

  function handleAddCartItemTick(newItem) {
    const nonExist = newItem.filter(item => 
        !cartItemTick.some(c => c.skuId === item.skuId)
      );
    setCartItemTick(cartItemTick => [...cartItemTick,...nonExist]);
  }
  function handleUpdateQuantity(skuId,quantity) {
    setCartItemTick(cartItemTick => cartItemTick.map(item => item.skuId ===skuId ? {...item,quantity} : item))
  }
  function handleRemoveCartItemTick(removeItem) {
    console.log(removeItem)
    setCartItemTick(cartItemTick => cartItemTick.filter(item => !removeItem.includes(item.skuId)))
  }
  function handleRemoveAll() {
    setCartItemTick([])
  }

  return (
    <CartContext.Provider value={{ cartItemTick, handleRemoveAll,handleUpdateQuantity,handleAddCartItemTick, handleRemoveCartItemTick}}>
      {children}
    </CartContext.Provider>
  );
}

function useCartContext() {
  const context = useContext(CartContext);
  if (context === undefined)
    throw new Error("CartContext was used outside of CartContextProvider");
  return context;
}

export { CartContextProvider, useCartContext };
