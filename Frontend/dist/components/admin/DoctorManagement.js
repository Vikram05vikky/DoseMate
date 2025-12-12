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
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = __importStar(require("react"));
var AppContext_1 = require("../../context/AppContext");
var lucide_react_1 = require("lucide-react");
var types_1 = require("../../types");
var DoctorManagement = function () {
    var _a = (0, AppContext_1.useApp)(), doctors = _a.doctors, setDoctors = _a.setDoctors;
    var _b = react_1.useState < 'all' | 'pending' | 'approved' | 'rejected' > ('all'), filter = _b[0], setFilter = _b[1];
    var filteredDoctors = doctors.filter(function (doctor) {
        return filter === 'all' || doctor.status === filter;
    });
    var handleApprove = function (doctorId) {
        setDoctors(doctors.map(function (doctor) {
            return doctor.id === doctorId ? __assign(__assign({}, doctor), { status: 'approved' }) : doctor;
        }));
    };
    var handleReject = function (doctorId) {
        setDoctors(doctors.map(function (doctor) {
            return doctor.id === doctorId ? __assign(__assign({}, doctor), { status: 'rejected' }) : doctor;
        }));
    };
    var handleDelete = function (doctorId) {
        if (window.confirm('Are you sure you want to delete this doctor?')) {
            setDoctors(doctors.filter(function (doctor) { return doctor.id !== doctorId; }));
        }
    };
    var getStatusColor = function (status) {
        switch (status) {
            case 'approved': return 'bg-green-100 text-green-800';
            case 'rejected': return 'bg-red-100 text-red-800';
            case 'pending': return 'bg-yellow-100 text-yellow-800';
            default: return 'bg-gray-100 text-gray-800';
        }
    };
    return (<div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold text-gray-900">Doctor Management</h2>
        <div className="flex space-x-2">
          {['all', 'pending', 'approved', 'rejected'].map(function (status) { return (<button key={status} onClick={function () { return setFilter(status); }} className={"px-4 py-2 rounded-lg text-sm font-medium transition-colors ".concat(filter === status
                ? 'bg-blue-600 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200')}>
              {status.charAt(0).toUpperCase() + status.slice(1)}
              {status !== 'all' && (<span className="ml-2 bg-white bg-opacity-20 px-2 py-1 rounded-full text-xs">
                  {doctors.filter(function (d) { return d.status === status; }).length}
                </span>)}
            </button>); })}
        </div>
      </div>

      <div className="grid grid-cols-1 gap-6">
        {filteredDoctors.map(function (doctor) { return (<div key={doctor.id} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
            <div className="flex items-center justify-between mb-4">
              <div className="flex items-center space-x-4">
                <div className="bg-blue-100 p-3 rounded-lg">
                  <lucide_react_1.Award className="h-6 w-6 text-blue-600"/>
                </div>
                <div>
                  <h3 className="text-lg font-semibold text-gray-900">{doctor.name}</h3>
                  <p className="text-gray-600">{doctor.specialization}</p>
                </div>
              </div>
              <span className={"px-3 py-1 rounded-full text-sm font-medium ".concat(getStatusColor(doctor.status))}>
                {doctor.status}
              </span>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
              <div className="flex items-center space-x-2 text-gray-600">
                <lucide_react_1.Mail className="h-4 w-4"/>
                <span className="text-sm">{doctor.email}</span>
              </div>
              <div className="flex items-center space-x-2 text-gray-600">
                <lucide_react_1.Phone className="h-4 w-4"/>
                <span className="text-sm">{doctor.phone}</span>
              </div>
              <div className="flex items-center space-x-2 text-gray-600">
                <lucide_react_1.Calendar className="h-4 w-4"/>
                <span className="text-sm">Registered: {new Date(doctor.registrationDate).toLocaleDateString()}</span>
              </div>
            </div>

            <div className="flex items-center justify-between">
              <div className="text-sm text-gray-600">
                <span className="font-medium">License:</span> {doctor.licenseNumber}
              </div>
              <div className="flex space-x-2">
                {doctor.status === 'pending' && (<>
                    <button onClick={function () { return handleApprove(doctor.id); }} className="flex items-center space-x-2 bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 transition-colors">
                      <lucide_react_1.CheckCircle className="h-4 w-4"/>
                      <span>Approve</span>
                    </button>
                    <button onClick={function () { return handleReject(doctor.id); }} className="flex items-center space-x-2 bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition-colors">
                      <lucide_react_1.XCircle className="h-4 w-4"/>
                      <span>Reject</span>
                    </button>
                  </>)}
                <button onClick={function () { return handleDelete(doctor.id); }} className="flex items-center space-x-2 bg-gray-600 text-white px-4 py-2 rounded-lg hover:bg-gray-700 transition-colors">
                  <lucide_react_1.Trash2 className="h-4 w-4"/>
                  <span>Delete</span>
                </button>
              </div>
            </div>
          </div>); })}
      </div>

      {filteredDoctors.length === 0 && (<div className="text-center py-12">
          <div className="bg-gray-100 rounded-full p-3 w-16 h-16 mx-auto mb-4">
            <lucide_react_1.Award className="h-10 w-10 text-gray-400 mx-auto"/>
          </div>
          <h3 className="text-lg font-medium text-gray-900 mb-2">No doctors found</h3>
          <p className="text-gray-600">No doctors match the selected filter criteria.</p>
        </div>)}
    </div>);
};
exports.default = DoctorManagement;
