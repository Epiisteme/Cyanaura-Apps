import {Map, View} from 'ol';
import TileLayer from 'ol/layer/Tile';
import TileWMS from 'ol/source/TileWMS'
import OSM from 'ol/source/OSM';
import XYZ from 'ol/source/XYZ';
const ipfs = require('ipfs')
const Repo = require('ipfs-repo')
const all = require('it-all')

var node = null
var map = null
var filehash = null
var data = null
var png_cid = null

var ipfs_geoswarm_hash ='QmWPvCEnSkGR8iTs6uiZSNkbupbv7sz3MV5mKW3erHYVZa';
// var projection = get('EPSG:4326');
var resolutions = [
    1.40625,
    0.703125,
    0.3515625,
    0.17578125,
    0.087890625,
    0.0439453125,
    0.02197265625,
    0.010986328125,
    0.0054931640625,
    0.00274658203125
];

function getParameterByName(param, url) {
  if (!url) url = window.location.href;

  var results = new RegExp('[\\?&]' + param + '=([^&#]*)').exec(url);
  if (!results) return undefined;
  
  return results[1] || undefined;
}

const initNode = async() => {
  node = await ipfs.create(
    { 
      config: { Addresses: { Swarm: [] } },
    repo: new Repo('/tmp/custom-repo/.ipfs'),
  })

  console.log('node created')

  // try {
  //   await node.start()
  //   console.log('Node started!')
  // } catch (error) {
  //   console.error('Node failed to start!', error)
  // }
  var version = await node.version
  console.log(version)
  var isonline = await node.isOnline()
  console.log(isonline)
  
  var peerInfos = await node.swarm.peers;
  console.log(peerInfos.length)
  // if(map==null && peerInfos.length>4) {
    
    if(map==null) {
        // map = new Map({
        //   target: 'map',
        //   layers: [
        //     new TileLayer({
        //       source: new XYZ({
        //         url: 'https://{a-c}.tile.openstreetmap.org/{z}/{x}/{y}.png'
        //       })
        //     })
        //   ],
        //   view: new View({
        //     center: [0, 0],
        //     zoom: 2
        //   })
        // });
      
    var z = null
    map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM()
        }),
        new TileLayer({
          extent: [-180, -90, 180, 90],

          // extent: [-13884991, 2870341, -7455066, 6338219],
          source: new TileWMS({
            url: 'https://ahocevar.com/geoserver/wms',
            params: {'LAYERS':'World'},
            serverType: 'geoserver',
            // Countries have transparency, so do not fade tiles:
            transition: 0
          })
        })
                  // url: '/ipfs/QmWPvCEnSkGR8iTs6uiZSNkbupbv7sz3MV5mKW3erHYVZa',
                  // params: { 'LAYERS': 'world' },
                  // tileLoadFunction: function (imageTile, src) {
//                       var bbox = getParameterByName('BBOX', src);
//                       var extent = bbox.split("%2C");
//                       var res = (extent[2] - extent[0]) / 256;
//                       for (z = 0; z < resolutions.length; z++)
//                           if (res == resolutions[z]) break;
//                       var x = -270;
//                       var y = -180;

//                       x = extent[0] - x;
//                       y = extent[1] - y;

//                       x /= extent[2] - extent[0];
//                       y /= extent[3] - extent[1];

//                       x=Math.floor(Math.abs(x))
//                       y=Math.floor(y)
//                       var cache = '/ipfs/' + ipfs_geoswarm_hash;
//                       cache += '/world/z' + z + '/' + x + '_' + y + '.png';

//                       console.log(cache);

//                       node.files.get(cache, function (err, files) {
//                           files.forEach((file) => {
//                               console.log('GOT => ' + file.path);
//                               imageTile.getImage().src = 'data:image/png;base64,' + file.content.toString('base64');
//                           })
//                           err => {
//                             console.log('error in downloading files' + err)
//                           }
//                       });

                  // }
          //     })
          // })
      ],
      view: new View({
          // projection: projection,
          center: [0, 0],
          zoom: 0
      })
  });
}

console.log(map)

}

initNode()

const addFile = async (imgdata) => {
  for await (const file of node.add({
        path: 'img-data.txt',
        content: Buffer.from(imgdata)
      })) {
        // Log out the added files metadata and cat the file from IPFS
        console.log('\nAdded file:', file.path, file.cid)
        png_cid = file.cid

      }
}

document.getElementById('export-png').addEventListener('click', function() {
  map.once('rendercomplete', function() {
    var mapCanvas = document.createElement('canvas');
    var size = map.getSize();
    mapCanvas.width = size[0];
    mapCanvas.height = size[1];
    var mapContext = mapCanvas.getContext('2d');
    Array.prototype.forEach.call(document.querySelectorAll('.ol-layer canvas'), function(canvas) {
      if (canvas.width > 0) {
        var opacity = canvas.parentNode.style.opacity;
        mapContext.globalAlpha = opacity === '' ? 1 : Number(opacity);
        var transform = canvas.style.transform;
        // Get the transform parameters from the style's transform matrix
        var matrix = transform.match(/^matrix\(([^\(]*)\)$/)[1].split(',').map(Number);
        // Apply the transform to the export map context
        CanvasRenderingContext2D.prototype.setTransform.apply(mapContext, matrix);
        mapContext.drawImage(canvas, 0, 0);
      }
    });
    if (navigator.msSaveBlob) {
      // link download attribuute does not work on MS browsers
      navigator.msSaveBlob(mapCanvas.msToBlob(), 'map.png');
    } else {
      var link = document.getElementById('image-download');
      link.href = mapCanvas.toDataURL("image/png");
      console.log(link.href)

      addFile((link.href).toString())
    }
  });
  map.renderSync();

  document.getElementById('get-png').style.display = "block";
});


document.getElementById('get-png').addEventListener('click', async()=> {

  console.log(png_cid)
  data = Buffer.concat(await all(node.cat(png_cid)))
  console.log('\nFetched file content:')
  console.log(data.toString().substr(0, 1000).concat('...'))

  var img_b64 = data.toString()
  var png = img_b64.split(',')[1];

  var the_file = new Blob([window.atob(png)],  {type: 'image/png', encoding: 'utf-8'});

  var fr = new FileReader();
  fr.onload = function ( oFREvent ) {
      var v = oFREvent.target.result.split(',')[1]; // encoding is messed up here, so we fix it
      v = atob(v);
      var good_b64 = btoa(decodeURIComponent(escape(v)));
      document.getElementById("uploadPreview").src = "data:image/png;base64," + good_b64;
  };
  fr.readAsDataURL(the_file);
});