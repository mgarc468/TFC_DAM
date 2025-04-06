import React, { useState } from 'react';

const ProjectForm = () => {
  // Estados para los datos del formulario
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [presupuesto, setPresupuesto] = useState('');
  const [costeInterno, setCosteInterno] = useState('');
  const [costeExterno, setCosteExterno] = useState('');
  const [faseActual, setFaseActual] = useState('');

  // Esta es la función que se encargará de enviar el formulario
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Calculamos el coste total
    const costeTotal = parseFloat(costeInterno) + parseFloat(costeExterno);

    // Preparamos el objeto de datos a enviar al servidor
    const proyecto = {
      nombre,
      descripcion,
      presupuesto_estimado: parseFloat(presupuesto),
      coste_interno: parseFloat(costeInterno),
      coste_externo: parseFloat(costeExterno),
      coste_total: costeTotal, // El coste total se calcula automáticamente
      fase_actual: faseActual,
      creado_por: 1, // Este valor puede ser dinámico según el usuario autenticado
      fecha_creacion: new Date().toISOString(), // Usamos la fecha y hora actual
    };

    try {
      // Enviamos los datos a la API usando fetch
      const response = await fetch('http://localhost:8080/proyecto/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Indicamos que el cuerpo es JSON
        },
        body: JSON.stringify(proyecto), // Convertimos el objeto a una cadena JSON
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Proyecto creado:', data);
        alert('Proyecto creado con éxito!');
      } else {
        console.error('Error al crear el proyecto', response.statusText);
        alert('Error al crear el proyecto.');
      }
    } catch (error) {
      console.error('Error en la solicitud', error);
      alert('Hubo un error al enviar los datos.');
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nombre del Proyecto</label>
          <input 
            type="text" 
            value={nombre} 
            onChange={(e) => setNombre(e.target.value)} 
          />
        </div>
        <div>
          <label>Descripción</label>
          <input 
            type="text" 
            value={descripcion} 
            onChange={(e) => setDescripcion(e.target.value)} 
          />
        </div>
        <div>
          <label>Presupuesto Estimado</label>
          <input 
            type="number" 
            value={presupuesto} 
            onChange={(e) => setPresupuesto(e.target.value)} 
          />
        </div>
        <div>
          <label>Coste Interno</label>
          <input 
            type="number" 
            value={costeInterno} 
            onChange={(e) => setCosteInterno(e.target.value)} 
          />
        </div>
        <div>
          <label>Coste Externo</label>
          <input 
            type="number" 
            value={costeExterno} 
            onChange={(e) => setCosteExterno(e.target.value)} 
          />
        </div>
        <div>
          <label>Fase Actual</label>
          <input 
            type="text" 
            value={faseActual} 
            onChange={(e) => setFaseActual(e.target.value)} 
          />
        </div>
        <button type="submit">Crear Proyecto</button>
      </form>
    </div>
  );
};

export default ProjectForm;
