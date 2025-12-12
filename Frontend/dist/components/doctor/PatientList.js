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
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = __importStar(require("react"));
var AppContext_1 = require("../../context/AppContext");
var lucide_react_1 = require("lucide-react");
var PatientList = function () {
    var _a = (0, AppContext_1.useApp)(), patients = _a.patients, prescriptions = _a.prescriptions;
    var _b = (0, react_1.useState)(''), searchTerm = _b[0], setSearchTerm = _b[1];
    var _c = react_1.useState < string | null > (null), selectedPatient = _c[0], setSelectedPatient = _c[1];
    var currentDoctorId = '1'; // This would be user.id in a real app
    var doctorPatients = patients.filter(function (p) { return p.doctorId === currentDoctorId; });
    var filteredPatients = doctorPatients.filter(function (patient) {
        return patient.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
            patient.email.toLowerCase().includes(searchTerm.toLowerCase());
    });
    var getPatientStatus = function (patientId) {
        var patientPrescriptions = prescriptions.filter(function (p) { return p.patientId === patientId; });
        var hasActive = patientPrescriptions.some(function (p) { return p.status === 'active'; });
        return hasActive ? 'active' : 'completed';
    };
    var getPatientPrescriptionCount = function (patientId) {
        return prescriptions.filter(function (p) { return p.patientId === patientId; }).length;
    };
    return (<div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold text-gray-900">My Patients</h2>
        <div className="text-sm text-gray-600">
          Total: {doctorPatients.length} patients
        </div>
      </div>

      <div className="relative">
        <lucide_react_1.Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400"/>
        <input type="text" placeholder="Search patients by name or email..." value={searchTerm} onChange={function (e) { return setSearchTerm(e.target.value); }} className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {filteredPatients.map(function (patient) {
            var status = getPatientStatus(patient.id);
            var prescriptionCount = getPatientPrescriptionCount(patient.id);
            return (<div key={patient.id} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow">
              <div className="flex items-center justify-between mb-4">
                <div className="flex items-center space-x-3">
                  <div className="bg-blue-100 p-2 rounded-lg">
                    <lucide_react_1.User className="h-5 w-5 text-blue-600"/>
                  </div>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900">{patient.name}</h3>
                    <p className="text-sm text-gray-600">{patient.age} years, {patient.gender}</p>
                  </div>
                </div>
                <div className="flex items-center space-x-2">
                  <span className={"px-3 py-1 rounded-full text-xs font-medium ".concat(status === 'active'
                    ? 'bg-green-100 text-green-800'
                    : 'bg-gray-100 text-gray-800')}>
                    {status === 'active' ? (<div className="flex items-center">
                        <lucide_react_1.Clock className="h-3 w-3 mr-1"/>
                        Active Treatment
                      </div>) : (<div className="flex items-center">
                        <lucide_react_1.CheckCircle className="h-3 w-3 mr-1"/>
                        Completed
                      </div>)}
                  </span>
                </div>
              </div>

              <div className="grid grid-cols-1 gap-3 mb-4">
                <div className="flex items-center space-x-2 text-gray-600">
                  <lucide_react_1.Phone className="h-4 w-4"/>
                  <span className="text-sm">{patient.mobile}</span>
                </div>
                <div className="flex items-center space-x-2 text-gray-600">
                  <lucide_react_1.Mail className="h-4 w-4"/>
                  <span className="text-sm">{patient.email}</span>
                </div>
                <div className="flex items-center space-x-2 text-gray-600">
                  <lucide_react_1.Calendar className="h-4 w-4"/>
                  <span className="text-sm">Registered: {new Date(patient.registrationDate).toLocaleDateString()}</span>
                </div>
              </div>

              <div className="border-t border-gray-200 pt-4">
                <div className="flex items-center justify-between">
                  <div>
                    <span className="text-sm text-gray-600">Total Prescriptions</span>
                    <p className="text-lg font-semibold text-blue-600">{prescriptionCount}</p>
                  </div>
                  <div className="flex space-x-2">
                    <button onClick={function () { return setSelectedPatient(patient.id); }} className="flex items-center space-x-1 text-blue-600 hover:text-blue-700 transition-colors">
                      <lucide_react_1.Eye className="h-4 w-4"/>
                      <span className="text-sm">View Details</span>
                    </button>
                    <button className="flex items-center space-x-1 text-gray-600 hover:text-gray-700 transition-colors">
                      <lucide_react_1.Edit2 className="h-4 w-4"/>
                      <span className="text-sm">Edit</span>
                    </button>
                  </div>
                </div>
              </div>
            </div>);
        })}
      </div>

      {filteredPatients.length === 0 && (<div className="text-center py-12">
          <div className="bg-gray-100 rounded-full p-3 w-16 h-16 mx-auto mb-4">
            <lucide_react_1.User className="h-10 w-10 text-gray-400 mx-auto"/>
          </div>
          <h3 className="text-lg font-medium text-gray-900 mb-2">No patients found</h3>
          <p className="text-gray-600">
            {searchTerm ? 'Try adjusting your search terms.' : 'You haven\'t added any patients yet.'}
          </p>
        </div>)}
    </div>);
};
exports.default = PatientList;
