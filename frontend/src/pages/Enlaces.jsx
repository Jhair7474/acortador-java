import { useEffect, useState } from 'react';
import '../css/Enlaces.css'; // Importamos los nuevos estilos

const Enlaces = () => {
  const [links, setLinks] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/links')
      .then(res => res.json())
      .then(data => setLinks(data))
      .catch(err => console.error("Error cargando enlaces:", err));
  }, []);

  return (
    <div className="enlaces-container">
      <h2 className="enlaces-title">Listado de Enlaces</h2>
      <table className="links-table">
        <thead>
          <tr>
            <th>Imagen</th>
            <th>Enlace Original</th>
            <th>Descripción</th>
            <th>Enlace Acortado</th>
          </tr>
        </thead>
        <tbody>
          {links.map((link, i) => (
            <tr key={i}>
              <td>
                <img src={link.imageUrl} alt="preview" className="img-preview" />
              </td>
              <td className="desc-text">{link.originalUrl}</td>
              <td className="desc-text">{link.description}</td>
              <td>
                <a 
                  href={link.shortenedUrl} 
                  target="_blank" 
                  rel="noopener noreferrer" 
                  className="link-anchor"
                >
                  {link.shortenedUrl}
                </a>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Enlaces;