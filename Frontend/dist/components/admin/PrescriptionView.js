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
var PrescriptionView = function () {
    var _a = (0, AppContext_1.useApp)(), prescriptions = _a.prescriptions, patients = _a.patients, doctors = _a.doctors, diagnoses = _a.diagnoses;
    var _b = react_1.useState < 'all' | 'active' | 'completed' > ('all'), selectedStatus = _b[0], setSelectedStatus = _b[1];
    var filteredPrescriptions = prescriptions.filter(function (prescription) {
        return selectedStatus === 'all' || prescription.status === selectedStatus;
    });
    var getPatientName = function (patientId) {
        var patient = patients.find(function (p) { return p.id === patientId; });
        return patient ? patient.name : 'Unknown Patient';
    };
    var getDoctorName = function (doctorId) {
        var doctor = doctors.find(function (d) { return d.id === doctorId; });
        return doctor ? doctor.name : 'Unknown Doctor';
    };
    var getDiagnosis = function (diagnosisId) {
        var diagnosis = diagnoses.find(function (d) { return d.id === diagnosisId; });
        return diagnosis || null;
    };
    var getSeverityColor = function (severity) {
        switch (severity) {
            case 'low': return 'bg-green-100 text-green-800';
            case 'moderate': return 'bg-yellow-100 text-yellow-800';
            case 'severe': return 'bg-red-100 text-red-800';
            default: return 'bg-gray-100 text-gray-800';
        }
    };
    return (<div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold text-gray-900">Prescription Overview</h2>
        <div className="flex space-x-2">
          {['all', 'active', 'completed'].map(function (status) { return (<button key={status} onClick={function () { return setSelectedStatus(status); }} className={"px-4 py-2 rounded-lg text-sm font-medium transition-colors ".concat(selectedStatus === status
                ? 'bg-blue-600 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200')}>
              {status.charAt(0).toUpperCase() + status.slice(1)}
              <span className="ml-2 bg-white bg-opacity-20 px-2 py-1 rounded-full text-xs">
                {prescriptions.filter(function (p) { return status === 'all' || p.status === status; }).length}
              </span>
            </button>); })}
        </div>
      </div>

      <div className="space-y-6">
        {filteredPrescriptions.map(function (prescription) {
            var diagnosis = getDiagnosis(prescription.diagnosisId);
            return (<div key={prescription.id} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
              <div className="flex items-center justify-between mb-6">
                <div className="flex items-center space-x-4">
                  <div className="bg-blue-100 p-3 rounded-lg">
                    <lucide_react_1.FileText className="h-6 w-6 text-blue-600"/>
                  </div>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900">
                      Prescription #{prescription.id}
                    </h3>
                    <div className="flex items-center space-x-4 mt-1">
                      <span className="text-sm text-gray-600 flex items-center">
                        <lucide_react_1.User className="h-4 w-4 mr-1"/>
                        {getPatientName(prescription.patientId)}
                      </span>
                      <span className="text-sm text-gray-600">
                        by Dr. {getDoctorName(prescription.doctorId)}
                      </span>
                    </div>
                  </div>
                </div>
                <div className="text-right">
                  <span className={"px-3 py-1 rounded-full text-sm font-medium ".concat(prescription.status === 'active'
                    ? 'bg-green-100 text-green-800'
                    : 'bg-gray-100 text-gray-800')}>
                    {prescription.status}
                  </span>
                  <div className="text-sm text-gray-600 mt-1 flex items-center">
                    <lucide_react_1.Calendar className="h-4 w-4 mr-1"/>
                    {new Date(prescription.createdDate).toLocaleDateString()}
                  </div>
                </div>
              </div>

              {diagnosis && (<div className="bg-gray-50 rounded-lg p-4 mb-6">
                  <div className="flex items-center justify-between mb-2">
                    <h4 className="font-medium text-gray-900">Diagnosis</h4>
                    <span className={"px-2 py-1 rounded-full text-xs font-medium flex items-center ".concat(getSeverityColor(diagnosis.severityLevel))}>
                      <lucide_react_1.AlertTriangle className="h-3 w-3 mr-1"/>
                      {diagnosis.severityLevel}
                    </span>
                  </div>
                  <p className="text-sm text-gray-800 font-medium mb-1">{diagnosis.diagnosis}</p>
                  <p className="text-sm text-gray-600">{diagnosis.symptoms}</p>
                </div>)}

              <div className="space-y-4">
                <h4 className="font-medium text-gray-900 flex items-center">
                  <lucide_react_1.Pill className="h-4 w-4 mr-2"/>
                  Medications ({prescription.medications.length})
                </h4>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {prescription.medications.map(function (medication) { return (<div key={medication.id} className="border border-gray-200 rounded-lg p-4">
                      <div className="flex items-center justify-between mb-2">
                        <h5 className="font-medium text-gray-900">{medication.name}</h5>
                        <span className="text-sm text-gray-600">{medication.dosage}</span>
                      </div>
                      <div className="grid grid-cols-2 gap-2 text-sm text-gray-600">
                        <div>
                          <span className="font-medium">Quantity:</span> {medication.quantity}
                        </div>
                        <div>
                          <span className="font-medium">Duration:</span> {medication.duration}
                        </div>
                        <div className="flex items-center">
                          <lucide_react_1.Clock className="h-3 w-3 mr-1"/>
                          {medication.sessionsPerDay}x daily
                        </div>
                        <div>
                          <span className="font-medium">Food:</span> {medication.foodTiming} meals
                        </div>
                      </div>
                    </div>); })}
                </div>
              </div>
            </div>);
        })}
      </div>

      {filteredPrescriptions.length === 0 && (<div className="text-center py-12">
          <div className="bg-gray-100 rounded-full p-3 w-16 h-16 mx-auto mb-4">
            <lucide_react_1.FileText className="h-10 w-10 text-gray-400 mx-auto"/>
          </div>
          <h3 className="text-lg font-medium text-gray-900 mb-2">No prescriptions found</h3>
          <p className="text-gray-600">No prescriptions match the selected status filter.</p>
        </div>)}
    </div>);
};
exports.default = PrescriptionView;
