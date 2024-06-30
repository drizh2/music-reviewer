import PageContainer from "./components/PageContainer";
import React from "react";
import Edit from "../pages/edit";

const EditPage = (props) => {
    return (
        <PageContainer>
            <Edit {...props} />
        </PageContainer>
    );
};

export default EditPage;