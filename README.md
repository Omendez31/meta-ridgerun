meta-ridgerun
=============

RidgeRun vendor layer for Yocto/OE, kept minimal and ready for project-specific recipes and bbappends.

For a full RidgeRun Product List and links to their respective wikis and recipes in this layer. In case they are supported, see the following link: [RidgeRun Product List](https://developer.ridgerun.com/wiki/index.php/RidgeRun_Yocto_Developer_Guide/RidgeRun_Services_and_Development#RidgeRun_Product_List) 

Compatibility
-------------
- Yocto release: scarthgap
- LAYERSERIES_COMPAT: meta-ridgerun = "scarthgap"

Dependencies
------------
- core (poky)

Contents
--------
- `conf/layer.conf` — layer metadata and compatibility settings

Using the layer
---------------
From your build directory (e.g. `build`):
```
bitbake-layers add-layer ../meta-ridgerun
```
If you maintain `bblayers.conf` manually, append:
```
BBLAYERS += "${TOPDIR}/../meta-ridgerun"
```

Git Hooks
---------
This layer uses the `pre-commit` framework. Current checks:
- `10-compatible-machine.sh`: verifies each staged `.bb` file defines `COMPATIBLE_MACHINE`.

Install `pre-commit` and enable hooks from the `meta-ridgerun` repository root:
```
pip install pre-commit
pre-commit install --hook-type pre-commit --install-hooks
```

To add new validations, add a new executable script under `hooks/pre-commit.d/` and register it in `.pre-commit-config.yaml`.

Customer repositories
---------------------
Proprietary plugins in this layer fetch from private RidgeRun GitLab paths under `git@gitlab.ridgerun.com/ridgerun/orders/${RR_CUSTOMER_GITLAB_ORDER_DIR}`. Set the directory you were given in one of these ways (prefer `conf/local.conf`):
- In `conf/local.conf`: `RR_CUSTOMER_GITLAB_ORDER_DIR = "customer-1234"`
- From the environment (Yocto Scarthgap): `export RR_CUSTOMER_GITLAB_ORDER_DIR=customer-1234` and `export BB_ENV_PASSTHROUGH_ADDITIONS="$BB_ENV_PASSTHROUGH_ADDITIONS RR_CUSTOMER_GITLAB_ORDER_DIR"` before running BitBake

Support and contributions
-------------------------
For changes and questions, reach out to the RidgeRun Yocto maintenance team.
RidgeRun website: https://www.ridgerun.com/
Support email: support@ridgerun.com
