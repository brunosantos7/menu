import axios from "axios";
import { setGlobal } from "reactn";

const UNAUTHORIZED_ACCESS = 401;
const FORBIDDEN = 403;

let baseUrl = "http://localhost:8080";

const HttpService = axios.create({
  baseURL: baseUrl
});

HttpService.interceptors.request.use(
  config => {
    config.headers = {
      "Content-Type": "application/x-www-form-urlencoded"
    };
    config.headers.Authorization =
      "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODEyNzI2MTgsImlhdCI6MTU4MTE4NjIxOCwiZW1haWwiOiJicnVuby5zYW50b3M3QG91dGxvb2suY29tIn0.N0UcOIpaxYcdLO-UIFaeawwhd7bB3DOETUkorV0n3QQ";
    setGlobal({ requestLoading: true });
    return config;
  },
  error => {
    setGlobal({ requestLoading: false });
    return Promise.reject(error);
  }
);

HttpService.interceptors.response.use(
  response => {
    setGlobal({ requestLoading: false });
    return response.data;
  },
  error => {
    setGlobal({ requestLoading: false });

    if (error.response) {
      const { response } = error;

      if (
        response.status === UNAUTHORIZED_ACCESS ||
        response.status === FORBIDDEN
      ) {
        const message = response.data.message || "Acesso não autorizado.";

        setGlobal({
          notificationMessage: { type: "error", message }
        });
      } else if (response.status >= 400 && response.status < 499) {
        const body = response.data || {};
        let message = body.message || "Incorrect usage!";

        if (body.errors && body.errors.length > 0) {
          body.errors.forEach(el => {
            const field = el.field ? `${el.field}: ` : "";
            message += el.defaultMessage || "";
            message += field + message;
          });
        }

        setGlobal({
          notificationMessage: { type: "warning", message }
        });
      } else if (response.status >= 500 && response.status < 599) {
        setGlobal({
          notificationMessage: {
            type: "error",
            message: "Erro no Servidor. Por favor contate o time de suporte."
          }
        });
      }
    } else if (error) {
      setGlobal({
        notificationMessage: {
          type: "error",
          message: "Servidor indisponível."
        }
      });
    }
    return Promise.reject(error);
  }
);

export default HttpService;
