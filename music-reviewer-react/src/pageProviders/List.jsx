import PageContainer from "./components/PageContainer";
import React from "react";
import List from "../pages/list";

const ListPage = (props) => {
    return (
        <PageContainer>
            <List {...props} />
        </PageContainer>
    );
};

export default ListPage;