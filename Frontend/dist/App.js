"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || (function () {
    var ownKeys = function(o) {
        ownKeys = Object.getOwnPropertyNames || function (o) {
            var ar = [];
            for (var k in o) if (Object.prototype.hasOwnProperty.call(o, k)) ar[ar.length] = k;
            return ar;
        };
        return ownKeys(o);
    };
    return function (mod) {
        if (mod && mod.__esModule) return mod;
        var result = {};
        if (mod != null) for (var k = ownKeys(mod), i = 0; i < k.length; i++) if (k[i] !== "default") __createBinding(result, mod, k[i]);
        __setModuleDefault(result, mod);
        return result;
    };
})();
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = __importStar(require("react"));
var AppContext_1 = require("./context/AppContext");
var Layout_1 = __importDefault(require("./components/Layout"));
var Navigation_1 = __importDefault(require("./components/Navigation"));
// Auth Components
var AdminLogin_1 = __importDefault(require("./components/auth/AdminLogin"));
var DoctorLogin_1 = __importDefault(require("./components/auth/DoctorLogin"));
var DoctorRegister_1 = __importDefault(require("./components/auth/DoctorRegister"));
// Admin Components
var AdminDashboard_1 = __importDefault(require("./components/admin/AdminDashboard"));
var DoctorManagement_1 = __importDefault(require("./components/admin/DoctorManagement"));
var PatientOverview_1 = __importDefault(require("./components/admin/PatientOverview"));
var PrescriptionView_1 = __importDefault(require("./components/admin/PrescriptionView"));
// Doctor Components
var DoctorDashboard_1 = __importDefault(require("./components/doctor/DoctorDashboard"));
var AddPatient_1 = __importDefault(require("./components/doctor/AddPatient"));
var PatientList_1 = __importDefault(require("./components/doctor/PatientList"));
var AppContent = function () {
    var _a = (0, AppContext_1.useApp)(), user = _a.user, adminLogin = _a.adminLogin, doctorLogin = _a.doctorLogin, doctorRegister = _a.doctorRegister;
    var _b = (0, react_1.useState)('dashboard'), activeTab = _b[0], setActiveTab = _b[1];
    var _c = react_1.useState < 'admin-login' | 'doctor-login' | 'doctor-register' > ('admin-login'), authView = _c[0], setAuthView = _c[1];
    var renderContent = function () {
        if ((user === null || user === void 0 ? void 0 : user.role) === 'admin') {
            switch (activeTab) {
                case 'dashboard':
                    return <AdminDashboard_1.default />;
                case 'doctors':
                    return <DoctorManagement_1.default />;
                case 'patients':
                    return <PatientOverview_1.default />;
                case 'prescriptions':
                    return <PrescriptionView_1.default />;
                default:
                    return <AdminDashboard_1.default />;
            }
        }
        else if ((user === null || user === void 0 ? void 0 : user.role) === 'doctor') {
            switch (activeTab) {
                case 'dashboard':
                    return <DoctorDashboard_1.default />;
                case 'add-patient':
                    return <AddPatient_1.default />;
                case 'patient-list':
                    return <PatientList_1.default />;
                default:
                    return <DoctorDashboard_1.default />;
            }
        }
        return null;
    };
    if (!user) {
        switch (authView) {
            case 'admin-login':
                return (<AdminLogin_1.default onLogin={adminLogin} onSwitchToDoctor={function () { return setAuthView('doctor-login'); }}/>);
            case 'doctor-login':
                return (<DoctorLogin_1.default onLogin={doctorLogin} onSwitchToRegister={function () { return setAuthView('doctor-register'); }} onSwitchToAdmin={function () { return setAuthView('admin-login'); }}/>);
            case 'doctor-register':
                return (<DoctorRegister_1.default onRegister={doctorRegister} onSwitchToLogin={function () { return setAuthView('doctor-login'); }}/>);
            default:
                return null;
        }
    }
    return (<Layout_1.default>
      <Navigation_1.default activeTab={activeTab} onTabChange={setActiveTab}/>
      {renderContent()}
    </Layout_1.default>);
};
function App() {
    return (<AppContext_1.AppProvider>
      <AppContent />
    </AppContext_1.AppProvider>);
}
exports.default = App;
