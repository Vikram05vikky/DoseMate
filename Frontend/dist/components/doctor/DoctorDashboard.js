"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = __importDefault(require("react"));
var AppContext_1 = require("../../context/AppContext");
var lucide_react_1 = require("lucide-react");
var DoctorDashboard = function () {
    var _a = (0, AppContext_1.useApp)(), patients = _a.patients, prescriptions = _a.prescriptions, user = _a.user;
    // Filter data for current doctor (assuming user.id corresponds to doctor ID)
    var currentDoctorId = '1'; // This would be user.id in a real app
    var doctorPatients = patients.filter(function (p) { return p.doctorId === currentDoctorId; });
    var doctorPrescriptions = prescriptions.filter(function (p) { return p.doctorId === currentDoctorId; });
    var activePrescriptions = doctorPrescriptions.filter(function (p) { return p.status === 'active'; });
    var completedPrescriptions = doctorPrescriptions.filter(function (p) { return p.status === 'completed'; });
    var stats = [
        {
            title: 'Total Patients',
            value: doctorPatients.length,
            icon: lucide_react_1.Users,
            color: 'bg-blue-500',
            textColor: 'text-blue-600'
        },
        {
            title: 'Total Prescriptions',
            value: doctorPrescriptions.length,
            icon: lucide_react_1.FileText,
            color: 'bg-purple-500',
            textColor: 'text-purple-600'
        },
        {
            title: 'Active Treatments',
            value: activePrescriptions.length,
            icon: lucide_react_1.Activity,
            color: 'bg-green-500',
            textColor: 'text-green-600'
        },
        {
            title: 'Completed Treatments',
            value: completedPrescriptions.length,
            icon: lucide_react_1.CheckCircle,
            color: 'bg-teal-500',
            textColor: 'text-teal-600'
        }
    ];
    var recentPatients = doctorPatients.slice(-3);
    return (<div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold text-gray-900">Doctor Dashboard</h2>
        <div className="text-sm text-gray-600">
          Welcome back, Dr. {user === null || user === void 0 ? void 0 : user.name}!
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map(function (stat, index) {
            var Icon = stat.icon;
            return (<div key={index} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm font-medium text-gray-600">{stat.title}</p>
                  <p className="text-3xl font-bold text-gray-900 mt-2">{stat.value}</p>
                </div>
                <div className={"".concat(stat.color, " p-3 rounded-lg")}>
                  <Icon className="h-6 w-6 text-white"/>
                </div>
              </div>
              <div className="mt-4 flex items-center">
                <lucide_react_1.TrendingUp className="h-4 w-4 text-green-500 mr-2"/>
                <span className="text-sm text-green-600 font-medium">Active</span>
              </div>
            </div>);
        })}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Recent Patients</h3>
          <div className="space-y-4">
            {recentPatients.length > 0 ? recentPatients.map(function (patient) { return (<div key={patient.id} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                <div>
                  <h4 className="font-medium text-gray-900">{patient.name}</h4>
                  <p className="text-sm text-gray-600">{patient.age} years, {patient.gender}</p>
                </div>
                <div className="text-right">
                  <div className="flex items-center text-sm text-gray-600">
                    <lucide_react_1.Calendar className="h-4 w-4 mr-1"/>
                    {new Date(patient.registrationDate).toLocaleDateString()}
                  </div>
                </div>
              </div>); }) : (<p className="text-gray-500 text-center py-4">No patients registered yet</p>)}
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Treatment Overview</h3>
          <div className="space-y-4">
            <div className="flex items-center justify-between">
              <span className="text-gray-600">Active Treatments</span>
              <div className="flex items-center">
                <div className="bg-green-100 w-4 h-4 rounded-full mr-2"></div>
                <span className="font-medium text-gray-900">{activePrescriptions.length}</span>
              </div>
            </div>
            <div className="flex items-center justify-between">
              <span className="text-gray-600">Completed Treatments</span>
              <div className="flex items-center">
                <div className="bg-gray-100 w-4 h-4 rounded-full mr-2"></div>
                <span className="font-medium text-gray-900">{completedPrescriptions.length}</span>
              </div>
            </div>
            <div className="flex items-center justify-between">
              <span className="text-gray-600">Success Rate</span>
              <span className="font-medium text-green-600">
                {doctorPrescriptions.length > 0
            ? Math.round((completedPrescriptions.length / doctorPrescriptions.length) * 100)
            : 0}%
              </span>
            </div>
            <div className="flex items-center justify-between pt-2 border-t">
              <span className="text-gray-600">Total Patients Treated</span>
              <span className="font-bold text-blue-600">{doctorPatients.length}</span>
            </div>
          </div>
        </div>
      </div>

      <div className="bg-gradient-to-r from-blue-50 to-indigo-50 rounded-xl p-6 border border-blue-100">
        <h3 className="text-lg font-semibold text-gray-900 mb-2">Quick Actions</h3>
        <p className="text-gray-600 mb-4">Manage your practice efficiently with these common tasks.</p>
        <div className="flex flex-wrap gap-3">
          <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
            Add New Patient
          </button>
          <button className="bg-white text-blue-600 border border-blue-300 px-4 py-2 rounded-lg hover:bg-blue-50 transition-colors">
            View All Patients
          </button>
          <button className="bg-white text-blue-600 border border-blue-300 px-4 py-2 rounded-lg hover:bg-blue-50 transition-colors">
            Recent Prescriptions
          </button>
        </div>
      </div>
    </div>);
};
exports.default = DoctorDashboard;
