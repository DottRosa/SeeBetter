# SeeBetter

## TODO

- [ ] Simulare acromatopsia
- [ ] Raffinare daltonizzatore (Canny Edge detection).
- [ ] Si può migliorare il sobel, basta evitare di fare prima la conversione del frame in grigio e poi applicare il filtro. Mentre converto in grigio applico il filtro, nello stesso ciclo.
- [x] Fare menu filtri
- [x] Impostare layout quando un filtro è selezionato (si dovrebbero usare le selection)
- [x] Fare menu daltonizzazione
- [x] Creare immagini daltonizzazione menu
- [x] Fare filtro protanopia
- [ ] Fare filtro deuteranopia
- [ ] Fare filtro tritanopia
- [x] Fare filtro normale
- [ ] Fare impostazioni
- [ ] Fare info
- [ ] Fare animazioni
- [ ] Fare logo
- [ ] Sistemare tutto il codice
- [ ] Fare in modo di scattare foto e di salvarle
- [ ] Sistemare layout header bar (se cambia il nome non devono cambiare le distanze)


---

## RISORSE

1. Edge detection Android - https://blog.zaven.co/opencv-advanced-android-development-edge-detection/

1. Applicare filtri fotocamera - http://stackoverflow.com/questions/8371055/apply-custom-filters-to-camera-output

1. Gestione camera - https://inducesmile.com/android/android-camera-api-tutorial/

1. Reperimento bibliografia 1 - https://scholar.google.it/schhp?hl=it&as_sdt=0,5

1. Reperimento bibliografia 2 - http://dl.acm.org/dl.cfm?CFID=755454821&CFTOKEN=14310714

---

## RIFERIMENTI

1. Human – Computer Systems Interaction: Backgrounds and Applications 2, Parte 1 - https://books.google.it/books?id=UeQ8kR6lxXMC&pg=PA456&lpg=PA456&dq=tritanopia+lms&source=bl&ots=0f72eMvT-a&sig=FMZgHoYAs_QYDNrzNhVo9CPXi4U&hl=it&sa=X&sqi=2&ved=0ahUKEwiSgoO4hOjSAhWHWhQKHYTICoIQ6AEIVzAF#v=onepage&q=tritanopia%20lms&f=false

1. Color Image and Video Enhancement - https://books.google.it/books?id=DIiQCgAAQBAJ&pg=PA319&lpg=PA319&dq=tritanopia+lms&source=bl&ots=VyeahksBuW&sig=FzCD_IG4vKHBB0ZoHdqrgAvDvrY&hl=it&sa=X&sqi=2&ved=0ahUKEwiSgoO4hOjSAhWHWhQKHYTICoIQ6AEIbjAJ#v=onepage&q=tritanopia%20lms&f=false

1. cdisplay_colorblind.c - https://stuff.mit.edu/afs/sipb/project/gimp-2.2/src/gimp-2.2.11/modules/cdisplay_colorblind.c

1. Digital Video Colourmaps for Checking the Legibility of Displays by Dichromats - http://vision.psychol.cam.ac.uk/jdmollon/papers/colourmaps.pdf

1. Le test d’Ishihara - http://daltonien.free.fr/daltonien/article.php3?id_article=6

1. La visione dei colori - http://altrimondi.altervista.org/visione-dei-colori/

1. daltonize.py - https://moinmo.in/AccessibleMoin?action=AttachFile&do=view&target=daltonize.py

1. Canny edge detection - https://en.wikipedia.org/wiki/Canny_edge_detector

---

## PAROLE CHIAVE

1. Matrice di trasformazione di Viénot

---

## APP FEATURE

1. Prendere una foto dalla galleria e con il programma daltonizzarla (in questo caso si può fare lato server)

1. Le impostazioni per la daltonizzazione (solo bordi, solo daltonizzazione o tutti e due) lo metto nelle impostazioni. Immagine di esempio (devono essere 3 per far vedere tutte le tipologie) che cambia al variare delle due spunte. Se toglie tutte le spunte viene fuori un Toast che dice che non si può fare. (https://developer.android.com/guide/topics/ui/settings.html)

1. Possibilità di scegliere il colore dei bordi nella edge detection. Compare un pulsante che permette di scegliere il colore (http://stackoverflow.com/questions/8629535/implementing-a-slider-in-android).

1. Spunta per mostrare i frame al secondo (http://stackoverflow.com/questions/8907917/how-to-measure-fps-on-android-during-app-development)

1. Problema strech immagini (http://stackoverflow.com/questions/13901040/textures-are-stretched-in-android-opengl-es-2-0)

1. GLSL https://en.wikibooks.org/wiki/GLSL_Programming/Vector_and_Matrix_Operations - https://www.khronos.org/opengl/wiki/Data_Type_(GLSL)

---

## STATO DELL'ARTE

1. http://www.color-blindness.com/2010/12/13/20-iphone-apps-for-the-color-blind/

1. http://www.color-blindness.com/coblis-color-blindness-simulator/

