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
var __spreadArray = (this && this.__spreadArray) || function (to, from, pack) {
    if (pack || arguments.length === 2) for (var i = 0, l = from.length, ar; i < l; i++) {
        if (ar || !(i in from)) {
            if (!ar) ar = Array.prototype.slice.call(from, 0, i);
            ar[i] = from[i];
        }
    }
    return to.concat(ar || Array.prototype.slice.call(from));
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AppProvider = exports.useApp = void 0;
var react_1 = __importStar(require("react"));
var types_1 = require("../types");
var uuid_1 = require("uuid");
var AppContext = react_1.createContext < AppContextType | undefined > (undefined);
var useApp = function () {
    var context = (0, react_1.useContext)(AppContext);
    if (context === undefined) {
        throw new Error('useApp must be used within an AppProvider');
    }
    return context;
};
exports.useApp = useApp;
var AppProvider = function (_a) {
    var children = _a.children;
    var _b = react_1.useState < types_1.User | null > (null), user = _b[0], setUser = _b[1];
    var _c = react_1.useState < types_1.Doctor[] > ([
        {
            id: '1',
            name: 'Dr. Sarah Johnson',
            email: 'sarah.johnson@hospital.com',
            phone: '+1-555-0123',
            specialization: 'Cardiology',
            licenseNumber: 'MD123456',
            registrationDate: '2024-01-15',
            status: 'approved',
            password: 'doctor123'
        },
        {
            id: '2',
            name: 'Dr. Michael Chen',
            email: 'michael.chen@hospital.com',
            phone: '+1-555-0124',
            specialization: 'Dermatology',
            licenseNumber: 'MD789012',
            registrationDate: '2024-01-20',
            status: 'pending',
            password: 'doctor123'
        }
    ]), doctors = _c[0], setDoctors = _c[1];
    var _d = react_1.useState < types_1.Patient[] > ([
        {
            id: '1',
            name: 'John Smith',
            age: 45,
            gender: 'male',
            mobile: '+1-555-0201',
            email: 'john.smith@email.com',
            address: '123 Main St, City, State 12345',
            doctorId: '1',
            registrationDate: '2024-01-25'
        },
        {
            id: '2',
            name: 'Emily Davis',
            age: 32,
            gender: 'female',
            mobile: '+1-555-0202',
            email: 'emily.davis@email.com',
            address: '456 Oak Ave, City, State 12345',
            doctorId: '1',
            registrationDate: '2024-01-28'
        }
    ]), patients = _d[0], setPatients = _d[1];
    var _e = react_1.useState < types_1.Diagnosis[] > ([
        {
            id: '1',
            patientId: '1',
            diagnosis: 'Hypertension',
            symptoms: 'High blood pressure, headaches, dizziness',
            severityLevel: 'moderate',
            date: '2024-01-25'
        },
        {
            id: '2',
            patientId: '2',
            diagnosis: 'Migraine',
            symptoms: 'Severe headache, nausea, light sensitivity',
            severityLevel: 'severe',
            date: '2024-01-28'
        }
    ]), diagnoses = _e[0], setDiagnoses = _e[1];
    var _f = react_1.useState < types_1.Prescription[] > ([
        {
            id: '1',
            patientId: '1',
            doctorId: '1',
            diagnosisId: '1',
            medications: [
                {
                    id: '1',
                    name: 'Lisinopril',
                    quantity: '30 tablets',
                    sessionsPerDay: 1,
                    foodTiming: 'before',
                    dosage: '10mg',
                    duration: '30 days'
                },
                {
                    id: '2',
                    name: 'Hydrochlorothiazide',
                    quantity: '30 tablets',
                    sessionsPerDay: 1,
                    foodTiming: 'with',
                    dosage: '25mg',
                    duration: '30 days'
                }
            ],
            status: 'active',
            createdDate: '2024-01-25'
        },
        {
            id: '2',
            patientId: '2',
            doctorId: '1',
            diagnosisId: '2',
            medications: [
                {
                    id: '3',
                    name: 'Sumatriptan',
                    quantity: '9 tablets',
                    sessionsPerDay: 1,
                    foodTiming: 'after',
                    dosage: '50mg',
                    duration: 'As needed'
                }
            ],
            status: 'active',
            createdDate: '2024-01-28'
        }
    ]), prescriptions = _f[0], setPrescriptions = _f[1];
    var adminLogin = function (email, password) {
        // Demo admin credentials
        if (email === 'admin@hospital.com' && password === 'admin123') {
            setUser({
                id: 'admin-1',
                role: 'admin',
                name: 'Admin User',
                email: 'admin@hospital.com'
            });
            return true;
        }
        return false;
    };
    var doctorLogin = function (email, password) {
        var doctor = doctors.find(function (d) {
            return d.email === email &&
                d.password === password &&
                d.status === 'approved';
        });
        if (doctor) {
            setUser({
                id: doctor.id,
                role: 'doctor',
                name: doctor.name,
                email: doctor.email
            });
            return true;
        }
        return false;
    };
    var doctorRegister = function (doctorData) {
        var newDoctor = {
            id: (0, uuid_1.v4)(),
            name: doctorData.name,
            email: doctorData.email,
            phone: doctorData.phone,
            specialization: doctorData.specialization,
            licenseNumber: doctorData.licenseNumber,
            registrationDate: new Date().toISOString().split('T')[0],
            status: 'pending',
            password: doctorData.password
        };
        setDoctors(function (prev) { return __spreadArray(__spreadArray([], prev, true), [newDoctor], false); });
    };
    var logout = function () {
        setUser(null);
    };
    return (<AppContext.Provider value={{
            user: user,
            setUser: setUser,
            doctors: doctors,
            setDoctors: setDoctors,
            patients: patients,
            setPatients: setPatients,
            prescriptions: prescriptions,
            setPrescriptions: setPrescriptions,
            diagnoses: diagnoses,
            setDiagnoses: setDiagnoses,
            adminLogin: adminLogin,
            doctorLogin: doctorLogin,
            doctorRegister: doctorRegister,
            logout: logout
        }}>
      {children}
    </AppContext.Provider>);
};
exports.AppProvider = AppProvider;
