"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = require("react");
var client_1 = require("react-dom/client");
require("./index.css");
var App_jsx_1 = __importDefault(require("./App.jsx"));
(0, client_1.createRoot)(document.getElementById('root')).render(<react_1.StrictMode>
    <App_jsx_1.default />
  </react_1.StrictMode>);
