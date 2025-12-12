"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = __importDefault(require("react"));
var AppContext_1 = require("../../context/AppContext");
var lucide_react_1 = require("lucide-react");
var AdminDashboard = function () {
    var _a = (0, AppContext_1.useApp)(), doctors = _a.doctors, patients = _a.patients, prescriptions = _a.prescriptions;
    var approvedDoctors = doctors.filter(function (d) { return d.status === 'approved'; }).length;
    var pendingDoctors = doctors.filter(function (d) { return d.status === 'pending'; }).length;
    var activeTreatments = prescriptions.filter(function (p) { return p.status === 'active'; }).length;
    var completedTreatments = prescriptions.filter(function (p) { return p.status === 'completed'; }).length;
    var stats = [
        {
            title: 'Total Doctors',
            value: doctors.length,
            icon: lucide_react_1.Users,
            color: 'bg-blue-500',
            textColor: 'text-blue-600'
        },
        {
            title: 'Approved Doctors',
            value: approvedDoctors,
            icon: lucide_react_1.UserCheck,
            color: 'bg-green-500',
            textColor: 'text-green-600'
        },
        {
            title: 'Total Patients',
            value: patients.length,
            icon: lucide_react_1.Activity,
            color: 'bg-purple-500',
            textColor: 'text-purple-600'
        },
        {
            title: 'Active Treatments',
            value: activeTreatments,
            icon: lucide_react_1.TrendingUp,
            color: 'bg-orange-500',
            textColor: 'text-orange-600'
        },
        {
            title: 'Completed Treatments',
            value: completedTreatments,
            icon: lucide_react_1.FileText,
            color: 'bg-teal-500',
            textColor: 'text-teal-600'
        },
        {
            title: 'Pending Approvals',
            value: pendingDoctors,
            icon: lucide_react_1.Clock,
            color: 'bg-red-500',
            textColor: 'text-red-600'
        }
    ];
    return (<div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold text-gray-900">Admin Dashboard</h2>
        <div className="text-sm text-gray-600">
          Welcome back, Admin! Here's your system overview.
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
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
                <div className={"h-2 w-2 rounded-full ".concat(stat.color, " mr-2")}></div>
                <span className={"text-sm ".concat(stat.textColor, " font-medium")}>
                  System Active
                </span>
              </div>
            </div>);
        })}
      </div>

      {pendingDoctors > 0 && (<div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
          <div className="flex items-center">
            <lucide_react_1.Clock className="h-5 w-5 text-yellow-600 mr-2"/>
            <h3 className="text-sm font-medium text-yellow-800">
              Action Required: {pendingDoctors} doctor{pendingDoctors !== 1 ? 's' : ''} pending approval
            </h3>
          </div>
          <p className="text-sm text-yellow-700 mt-1">
            Review and approve pending doctor registrations to keep the system running smoothly.
          </p>
        </div>)}

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Recent Activity</h3>
          <div className="space-y-3">
            {doctors.slice(0, 3).map(function (doctor) { return (<div key={doctor.id} className="flex items-center space-x-3">
                <div className={"w-2 h-2 rounded-full ".concat(doctor.status === 'approved' ? 'bg-green-500' :
                doctor.status === 'pending' ? 'bg-yellow-500' : 'bg-red-500')}></div>
                <span className="text-sm text-gray-600">
                  Dr. {doctor.name} - {doctor.specialization}
                </span>
                <span className={"text-xs px-2 py-1 rounded-full ".concat(doctor.status === 'approved' ? 'bg-green-100 text-green-800' :
                doctor.status === 'pending' ? 'bg-yellow-100 text-yellow-800' : 'bg-red-100 text-red-800')}>
                  {doctor.status}
                </span>
              </div>); })}
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">System Health</h3>
          <div className="space-y-4">
            <div className="flex items-center justify-between">
              <span className="text-sm text-gray-600">Doctor Approval Rate</span>
              <span className="text-sm font-medium text-green-600">
                {doctors.length > 0 ? Math.round((approvedDoctors / doctors.length) * 100) : 0}%
              </span>
            </div>
            <div className="flex items-center justify-between">
              <span className="text-sm text-gray-600">Active Treatment Rate</span>
              <span className="text-sm font-medium text-blue-600">
                {prescriptions.length > 0 ? Math.round((activeTreatments / prescriptions.length) * 100) : 0}%
              </span>
            </div>
            <div className="flex items-center justify-between">
              <span className="text-sm text-gray-600">System Status</span>
              <span className="text-sm font-medium text-green-600">Operational</span>
            </div>
          </div>
        </div>
      </div>
    </div>);
};
exports.default = AdminDashboard;
