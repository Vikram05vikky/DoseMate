"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
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
var react_1 = __importStar(require("react"));
var AppContext_1 = require("../../context/AppContext");
var lucide_react_1 = require("lucide-react");
var uuid_1 = require("uuid");
var types_1 = require("../../types");
var AddPatient = function () {
    var _a = (0, AppContext_1.useApp)(), patients = _a.patients, setPatients = _a.setPatients, diagnoses = _a.diagnoses, setDiagnoses = _a.setDiagnoses, prescriptions = _a.prescriptions, setPrescriptions = _a.setPrescriptions;
    var _b = (0, react_1.useState)(1), currentStep = _b[0], setCurrentStep = _b[1];
    var _c = react_1.useState < PatientForm > ({
        name: '',
        age: 0,
        gender: 'male',
        mobile: '',
        email: '',
        address: ''
    }), patientForm = _c[0], setPatientForm = _c[1];
    var _d = react_1.useState < DiagnosisForm > ({
        diagnosis: '',
        symptoms: '',
        severityLevel: 'low'
    }), diagnosisForm = _d[0], setDiagnosisForm = _d[1];
    var _e = react_1.useState < MedicationForm[] > ([
        {
            name: '',
            quantity: '',
            sessionsPerDay: 1,
            foodTiming: 'before',
            dosage: '',
            duration: ''
        }
    ]), medications = _e[0], setMedications = _e[1];
    var handleAddMedication = function () {
        setMedications(__spreadArray(__spreadArray([], medications, true), [{
                name: '',
                quantity: '',
                sessionsPerDay: 1,
                foodTiming: 'before',
                dosage: '',
                duration: ''
            }], false));
    };
    var handleRemoveMedication = function (index) {
        if (medications.length > 1) {
            setMedications(medications.filter(function (_, i) { return i !== index; }));
        }
    };
    var handleMedicationChange = function (index, field, value) {
        var updatedMedications = medications.map(function (med, i) {
            var _a;
            return i === index ? __assign(__assign({}, med), (_a = {}, _a[field] = value, _a)) : med;
        });
        setMedications(updatedMedications);
    };
    var validateStep = function (step) {
        switch (step) {
            case 1:
                return patientForm.name && patientForm.age > 0 && patientForm.mobile && patientForm.email;
            case 2:
                return diagnosisForm.diagnosis && diagnosisForm.symptoms;
            case 3:
                return medications.every(function (med) { return med.name && med.quantity && med.dosage && med.duration; });
            default:
                return false;
        }
    };
    var handleSubmit = function () {
        var patientId = (0, uuid_1.v4)();
        var diagnosisId = (0, uuid_1.v4)();
        var prescriptionId = (0, uuid_1.v4)();
        var currentDoctorId = '1'; // This would be user.id in a real app
        // Create patient
        var newPatient = __assign(__assign({ id: patientId }, patientForm), { doctorId: currentDoctorId, registrationDate: new Date().toISOString().split('T')[0] });
        // Create diagnosis
        var newDiagnosis = __assign(__assign({ id: diagnosisId, patientId: patientId }, diagnosisForm), { date: new Date().toISOString().split('T')[0] });
        // Create medications with IDs
        var newMedications = medications.map(function (med) { return (__assign({ id: (0, uuid_1.v4)() }, med)); });
        // Create prescription
        var newPrescription = {
            id: prescriptionId,
            patientId: patientId,
            doctorId: currentDoctorId,
            diagnosisId: diagnosisId,
            medications: newMedications,
            status: 'active',
            createdDate: new Date().toISOString().split('T')[0]
        };
        // Update state
        setPatients(__spreadArray(__spreadArray([], patients, true), [newPatient], false));
        setDiagnoses(__spreadArray(__spreadArray([], diagnoses, true), [newDiagnosis], false));
        setPrescriptions(__spreadArray(__spreadArray([], prescriptions, true), [newPrescription], false));
        // Reset form
        setCurrentStep(1);
        setPatientForm({
            name: '',
            age: 0,
            gender: 'male',
            mobile: '',
            email: '',
            address: ''
        });
        setDiagnosisForm({
            diagnosis: '',
            symptoms: '',
            severityLevel: 'low'
        });
        setMedications([{
                name: '',
                quantity: '',
                sessionsPerDay: 1,
                foodTiming: 'before',
                dosage: '',
                duration: ''
            }]);
        alert('Patient added successfully!');
    };
    var steps = [
        { number: 1, title: 'Patient Information', icon: lucide_react_1.User },
        { number: 2, title: 'Diagnosis', icon: lucide_react_1.ClipboardList },
        { number: 3, title: 'Medications', icon: lucide_react_1.Pill }
    ];
    return (<div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold text-gray-900">Add New Patient</h2>
      </div>

      {/* Progress Steps */}
      <div className="flex items-center justify-center space-x-8 mb-8">
        {steps.map(function (step) {
            var Icon = step.icon;
            return (<div key={step.number} className="flex flex-col items-center">
              <div className={"w-12 h-12 rounded-full flex items-center justify-center ".concat(currentStep >= step.number
                    ? 'bg-blue-600 text-white'
                    : 'bg-gray-200 text-gray-600')}>
                <Icon className="h-6 w-6"/>
              </div>
              <span className={"mt-2 text-sm font-medium ".concat(currentStep >= step.number ? 'text-blue-600' : 'text-gray-600')}>
                {step.title}
              </span>
            </div>);
        })}
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-8">
        {/* Step 1: Patient Information */}
        {currentStep === 1 && (<div className="space-y-6">
            <h3 className="text-xl font-semibold text-gray-900 mb-6">Patient Information</h3>
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Full Name</label>
                <input type="text" value={patientForm.name} onChange={function (e) { return setPatientForm(__assign(__assign({}, patientForm), { name: e.target.value })); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="Enter patient's full name"/>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Age</label>
                <input type="number" value={patientForm.age} onChange={function (e) { return setPatientForm(__assign(__assign({}, patientForm), { age: parseInt(e.target.value) || 0 })); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="Enter age"/>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Gender</label>
                <select value={patientForm.gender} onChange={function (e) { return setPatientForm(__assign(__assign({}, patientForm), { gender: e.target.value })); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                  <option value="male">Male</option>
                  <option value="female">Female</option>
                  <option value="other">Other</option>
                </select>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Mobile Number</label>
                <input type="tel" value={patientForm.mobile} onChange={function (e) { return setPatientForm(__assign(__assign({}, patientForm), { mobile: e.target.value })); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="+1-555-0123"/>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Email Address</label>
                <input type="email" value={patientForm.email} onChange={function (e) { return setPatientForm(__assign(__assign({}, patientForm), { email: e.target.value })); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="patient@email.com"/>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Address</label>
                <textarea value={patientForm.address} onChange={function (e) { return setPatientForm(__assign(__assign({}, patientForm), { address: e.target.value })); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" rows={3} placeholder="Enter complete address"/>
              </div>
            </div>
          </div>)}

        {/* Step 2: Diagnosis */}
        {currentStep === 2 && (<div className="space-y-6">
            <h3 className="text-xl font-semibold text-gray-900 mb-6">Diagnosis Information</h3>
            
            <div className="space-y-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Diagnosis</label>
                <input type="text" value={diagnosisForm.diagnosis} onChange={function (e) { return setDiagnosisForm(__assign(__assign({}, diagnosisForm), { diagnosis: e.target.value })); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="Enter primary diagnosis"/>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Symptoms</label>
                <textarea value={diagnosisForm.symptoms} onChange={function (e) { return setDiagnosisForm(__assign(__assign({}, diagnosisForm), { symptoms: e.target.value })); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" rows={4} placeholder="Describe patient's symptoms in detail"/>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Severity Level</label>
                <div className="grid grid-cols-3 gap-4">
                  {['low', 'moderate', 'severe'].map(function (severity) { return (<label key={severity} className="flex items-center">
                      <input type="radio" name="severity" value={severity} checked={diagnosisForm.severityLevel === severity} onChange={function (e) { return setDiagnosisForm(__assign(__assign({}, diagnosisForm), { severityLevel: e.target.value })); }} className="mr-2"/>
                      <span className={"px-3 py-2 rounded-lg text-sm font-medium ".concat(severity === 'low' ? 'bg-green-100 text-green-800' :
                    severity === 'moderate' ? 'bg-yellow-100 text-yellow-800' :
                        'bg-red-100 text-red-800')}>
                        {severity.charAt(0).toUpperCase() + severity.slice(1)}
                      </span>
                    </label>); })}
                </div>
              </div>
            </div>
          </div>)}

        {/* Step 3: Medications */}
        {currentStep === 3 && (<div className="space-y-6">
            <div className="flex items-center justify-between">
              <h3 className="text-xl font-semibold text-gray-900">Medication Details</h3>
              <button onClick={handleAddMedication} className="flex items-center space-x-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
                <lucide_react_1.Plus className="h-4 w-4"/>
                <span>Add Medication</span>
              </button>
            </div>

            <div className="space-y-6">
              {medications.map(function (medication, index) { return (<div key={index} className="border border-gray-200 rounded-lg p-6">
                  <div className="flex items-center justify-between mb-4">
                    <h4 className="font-medium text-gray-900">Medication #{index + 1}</h4>
                    {medications.length > 1 && (<button onClick={function () { return handleRemoveMedication(index); }} className="text-red-600 hover:text-red-700 transition-colors">
                        <lucide_react_1.Trash2 className="h-4 w-4"/>
                      </button>)}
                  </div>

                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">Medicine Name</label>
                      <input type="text" value={medication.name} onChange={function (e) { return handleMedicationChange(index, 'name', e.target.value); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="Enter medicine name"/>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">Quantity</label>
                      <input type="text" value={medication.quantity} onChange={function (e) { return handleMedicationChange(index, 'quantity', e.target.value); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="e.g., 30 tablets"/>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">Sessions per Day</label>
                      <select value={medication.sessionsPerDay} onChange={function (e) { return handleMedicationChange(index, 'sessionsPerDay', parseInt(e.target.value)); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                        <option value={1}>1 time</option>
                        <option value={2}>2 times</option>
                        <option value={3}>3 times</option>
                        <option value={4}>4 times</option>
                      </select>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">Food Timing</label>
                      <select value={medication.foodTiming} onChange={function (e) { return handleMedicationChange(index, 'foodTiming', e.target.value); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                        <option value="before">Before meals</option>
                        <option value="after">After meals</option>
                        <option value="with">With meals</option>
                      </select>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">Dosage</label>
                      <input type="text" value={medication.dosage} onChange={function (e) { return handleMedicationChange(index, 'dosage', e.target.value); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="e.g., 10mg"/>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">Duration</label>
                      <input type="text" value={medication.duration} onChange={function (e) { return handleMedicationChange(index, 'duration', e.target.value); }} className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="e.g., 30 days"/>
                    </div>
                  </div>
                </div>); })}
            </div>
          </div>)}

        {/* Navigation Buttons */}
        <div className="flex items-center justify-between mt-8">
          <button onClick={function () { return setCurrentStep(Math.max(1, currentStep - 1)); }} disabled={currentStep === 1} className="px-6 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors disabled:opacity-50 disabled:cursor-not-allowed">
            Previous
          </button>

          {currentStep < 3 ? (<button onClick={function () { return setCurrentStep(currentStep + 1); }} disabled={!validateStep(currentStep)} className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed">
              Next
            </button>) : (<button onClick={handleSubmit} disabled={!validateStep(currentStep)} className="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed">
              Add Patient
            </button>)}
        </div>
      </div>
    </div>);
};
exports.default = AddPatient;
