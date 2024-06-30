import * as pages from './pages';
import config from 'config';

const result = {
  [pages.defaultPage]: `${config.UI_URL_PREFIX}/${pages.defaultPage}`,
  [pages.login]: `${config.UI_URL_PREFIX}/${pages.login}`,
  [pages.secretPage]: `${config.UI_URL_PREFIX}/${pages.secretPage}`,
  [pages.list]: `${config.UI_URL_PREFIX}/${pages.list}`,
  [pages.edit]: `${config.UI_URL_PREFIX}/${pages.edit}`
};

export default result;
