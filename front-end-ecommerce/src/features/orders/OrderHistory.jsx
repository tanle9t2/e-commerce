import React, { useState } from "react";
import styled from "styled-components";
import Button from "../../ui/Button";
import Highlight from "../../ui/Highlight";
import { useOrders } from "./useOrders";
import Spinner from "../../ui/Spinner";
import OrderCard from "./OderCard"
// Styled Components
const Container = styled.div`
  width: 100%;
  margin: auto;
`;

const FilterWrapper = styled.div`
  display: grid;
  grid-template-columns: 0.25fr 0.25fr 0.25fr 0.25fr 0.25fr 0.25fr;
  margin-bottom: 20px;
  background:var(--color-white);
  padding:15px;
`;

const FilterButton = styled.button`
  color: ${(props) => (props.active ? "var(--primary-color)" : "#000")};
  border: none;
  margin: 0 5px;
  cursor: pointer;
  border-radius: 5px;
`;
const OrderHistory = () => {
    const [filter, setFilter] = useState("all");
    const {isLoading,orders} = useOrders()
    if(isLoading) return <Spinner/>
    
    const filteredOrders =
        filter === "all" ? orders : orders.filter((o) => o.status === filter);
    return (
        <Container>
            <FilterWrapper>
                <FilterButton onClick={() => setFilter("all")} active={filter === "all"}>
                    Tất cả
                </FilterButton>
                <FilterButton onClick={() => setFilter("wait-payment")} active={filter === "wait-payment"}>
                    Chờ thanh toán
                </FilterButton>
                <FilterButton onClick={() => setFilter("1")} active={filter === "1"}>
                    Vận chuyển
                </FilterButton>
                <FilterButton onClick={() => setFilter("2")} active={filter === "2"}>
                    Chờ giao hàng
                </FilterButton>
                <FilterButton onClick={() => setFilter("completed")} active={filter === "completed"}>
                    Hoàn thành
                </FilterButton>
                <FilterButton onClick={() => setFilter("canceled")} active={filter === "canceled"}>
                    Đã hủy
                </FilterButton>
            </FilterWrapper>

            {orders.data.map((order) => (
              <OrderCard key={order.id} order={order}/>
            ))}
        </Container>
    );
};

export default OrderHistory;
