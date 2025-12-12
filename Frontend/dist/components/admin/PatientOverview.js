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
var PatientOverview = function () {
    var _a = (0, AppContext_1.useApp)(), patients = _a.patients, doctors = _a.doctors, prescriptions = _a.prescriptions;
    var _b = (0, react_1.useState)(''), searchTerm = _b[0], setSearchTerm = _b[1];
    var _c = (0, react_1.useState)('all'), selectedDoctor = _c[0], setSelectedDoctor = _c[1];
    var filteredPatients = patients.filter(function (patient) {
        var matchesSearch = patient.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
            patient.email.toLowerCase().includes(searchTerm.toLowerCase());
        var matchesDoctor = selectedDoctor === 'all' || patient.doctorId === selectedDoctor;
        return matchesSearch && matchesDoctor;
    });
    var getPatientTreatmentStatus = function (patientId) {
        var patientPrescriptions = prescriptions.filter(function (p) { return p.patientId === patientId; });
        var activeCount = patientPrescriptions.filter(function (p) { return p.status === 'active'; }).length;
        var completedCount = patientPrescriptions.filter(function (p) { return p.status === 'completed'; }).length;
        return { active: activeCount, completed: completedCount, total: patientPrescriptions.length };
    };
    var getDoctorName = function (doctorId) {
        var doctor = doctors.find(function (d) { return d.id === doctorId; });
        return doctor ? doctor.name : 'Unknown Doctor';
    };
    return (<div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold text-gray-900">Patient Overview</h2>
        <div className="text-sm text-gray-600">
          Total Patients: {patients.length}
        </div>
      </div>

      <div className="flex flex-col sm:flex-row gap-4">
        <div className="relative flex-1">
          <lucide_react_1.Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400"/>
          <input type="text" placeholder="Search patients by name or email..." value={searchTerm} onChange={function (e) { return setSearchTerm(e.target.value); }} className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
        </div>
        <div className="relative">
          <lucide_react_1.Filter className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400"/>
          <select value={selectedDoctor} onChange={function (e) { return setSelectedDoctor(e.target.value); }} className="pl-10 pr-8 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent bg-white">
            <option value="all">All Doctors</option>
            {doctors.map(function (doctor) { return (<option key={doctor.id} value={doctor.id}>{doctor.name}</option>); })}
          </select>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {filteredPatients.map(function (patient) {
            var treatmentStatus = getPatientTreatmentStatus(patient.id);
            return (<div key={patient.id} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
              <div className="flex items-center justify-between mb-4">
                <div className="flex items-center space-x-3">
                  <div className="bg-purple-100 p-2 rounded-lg">
                    <lucide_react_1.User className="h-5 w-5 text-purple-600"/>
                  </div>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900">{patient.name}</h3>
                    <p className="text-sm text-gray-600">{patient.age} years, {patient.gender}</p>
                  </div>
                </div>
                <div className="text-right">
                  <p className="text-sm font-medium text-gray-900">
                    Dr. {getDoctorName(patient.doctorId)}
                  </p>
                  <p className="text-xs text-gray-600">Attending Physician</p>
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
                  <lucide_react_1.MapPin className="h-4 w-4"/>
                  <span className="text-sm">{patient.address}</span>
                </div>
                <div className="flex items-center space-x-2 text-gray-600">
                  <lucide_react_1.Calendar className="h-4 w-4"/>
                  <span className="text-sm">Registered: {new Date(patient.registrationDate).toLocaleDateString()}</span>
                </div>
              </div>

              <div className="border-t border-gray-200 pt-4">
                <h4 className="text-sm font-medium text-gray-900 mb-2">Treatment Status</h4>
                <div className="grid grid-cols-3 gap-4">
                  <div className="text-center">
                    <p className="text-lg font-bold text-blue-600">{treatmentStatus.total}</p>
                    <p className="text-xs text-gray-600">Total</p>
                  </div>
                  <div className="text-center">
                    <p className="text-lg font-bold text-green-600">{treatmentStatus.active}</p>
                    <p className="text-xs text-gray-600">Active</p>
                  </div>
                  <div className="text-center">
                    <p className="text-lg font-bold text-gray-600">{treatmentStatus.completed}</p>
                    <p className="text-xs text-gray-600">Completed</p>
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
          <p className="text-gray-600">Try adjusting your search criteria or filters.</p>
        </div>)}
    </div>);
};
exports.default = PatientOverview;
