import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ProjectList from "./componentes/ProyectList";
import Dashboard from "./componentes/Dashboard";
import AddProject from "./componentes/AddProyect";
import Login from "./componentes/Login";
import NavBar from "./componentes/NavBar";
import RegisterUser from "./componentes/RegisterUser";
import ProjectCosts from "./componentes/ProyectCosts";

const App = () => {
  return (
    <Router>
      <div>
        <NavBar />
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/proyecto/list" element={<ProjectList />} />
          <Route path="/proyecto/dashboard" element={<Dashboard />} />
          <Route path="/add-project" element={<AddProject />} />
          <Route path="/register" element={<RegisterUser />} />
          <Route path="/costes" element={<ProjectCosts />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
