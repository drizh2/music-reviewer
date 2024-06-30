import useLocationSearch from "../../misc/hooks/useLocationSearch";
import React, {useMemo} from "react";
import getMessages from "../edit/intl";
import IntlProvider from "../../misc/providers/IntlProvider";
import Edit from "../edit/containers/Edit";

function Index(props) {
    const {
        lang,
    } = useLocationSearch();

    const messages = useMemo(() => getMessages(lang), [lang]);
    return (
        <IntlProvider messages={messages}>
            <Edit {...props}/>
        </IntlProvider>
    );
}

export default Index;