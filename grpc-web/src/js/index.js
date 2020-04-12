// const ol = require('ol')
import express from 'express'
const ipfs = require('ipfs')
const Repo = require('ipfs-repo')
const fs = require('fs-extra')
const all = require('it-all')

const app = express()

app.set('view engine', 'ejs')

var map  = null;
var node = null;
var status = null;

const initNode = async () => {

  node = await ipfs.create({
    start: false,
    config: {
      Addresses: {
          Swarm: [
              '/ip4/127.0.0.1/tcp/5001'
          ]
     }
  },
    repo: new Repo('/tmp/custom-repo/.ipfs'),
  })

  console.log('node created')

  try {
    await node.start()
    console.log('Node started!')
  } catch (error) {
    console.error('Node failed to start!', error)
  }
  console.log(node.version.toString())
  IPFS_Status()    

  // const { version } = await node.version()
//   console.log('Version:', version)
//   for await (const file of node.add({
//     path: 'test-data.txt',
//     content: Buffer.from('Hello World!')
//   })) {
//     // Log out the added files metadata and cat the file from IPFS
//     console.log('\nAdded file:', file.path, file.cid)

//     data = Buffer.concat(await all(node.cat(file.cid)))

//     // Print out the files contents to console
//     console.log('\nFetched file content:')
//     process.stdout.write(data)
//   }
// // data is returned as a Buffer, conver it back to a string
//  x= data.toString()
}

const IPFS_Status = async () => {
    var peerInfos = await node.swarm.peers;

    if(map==null && peerInfos.length>4) {
      map = true;
      
    var projection = ol.proj.get('EPSG:4326');
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

    map = new Map({
      target: 'map',
      layers: [
        new Tile({
          extent: [-180, -90, 180, 90],
          source: new TileWMS({
            url: '/ipfs/QmWPvCEnSkGR8iTs6uiZSNkbupbv7sz3MV5mKW3erHYVZa',
            params: { 'LAYERS': 'world' },
            tileLoadFunction: function (imageTile, src) {
              bbox = getParameterByName('BBOX', src);
              extent = bbox.split("%2C");
              res = (extent[2] - extent[0]) / 256;
              for (z = 0; z < resolutions.length; z++)
                if (res == resolutions[z]) break;
              x = -270;
              y = -180;

              x = extent[0] - x;
              y = extent[1] - y;

              x /= extent[2] - extent[0];
              y /= extent[3] - extent[1];

              cache = '/ipfs/' + ipfs_geoswarm_hash;
              cache += '/world/z' + z + '/' + x + '_' + y + '.png';

              console.log(cache);

              node.files.get(cache, function (err, files) {
                files.forEach((file) => {
                  console.log('GOT => ' + file.path);
                  imageTile.getImage().src = 'data:image/png;base64,' + file.content.toString('base64');
                })
              });

            }
          })
        })
      ],
      view: new View({
        projection: projection,
        center: [0, 0],
        zoom: 0
      })
    });
  }
}

app.get('/',  async (req, res) => {
  
  res.render('home', {showmap: map})
  var t = await initNode();
})

app.listen(8080, () => console.log('Example app listening on port 8080!'))

