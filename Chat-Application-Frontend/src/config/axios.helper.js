import axios from "axios";

export const BASEURL = import.meta.env.VITE_BACKEND_URL;

export const HttpClient = axios.create({
    baseURL: BASEURL,
});
