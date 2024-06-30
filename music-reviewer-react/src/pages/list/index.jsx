import React, {useMemo} from "react"
import useLocationSearch from "../../misc/hooks/useLocationSearch";
import IntlProvider from "../../misc/providers/IntlProvider";
import getMessages from "./intl";
import List from "./containers/List";

function Index(props) {
    const {
        lang,
    } = useLocationSearch();

    const messages = useMemo(() => getMessages(lang), [lang]);
    return (
      <IntlProvider messages={messages}>
        <List {...props}/>
      </IntlProvider>
    );
}

export default Index;